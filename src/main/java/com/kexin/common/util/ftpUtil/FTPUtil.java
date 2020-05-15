package com.kexin.common.util.ftpUtil;

import com.kexin.admin.entity.tables.Machine;
import com.kexin.admin.entity.tables.MachineModel;
import com.kexin.admin.entity.vo.Ftp;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;


/**
 * FTP 工具类
 */
public class FTPUtil {
    /**
     * 连接 FTP 服务器
     *
     * @param addr     FTP 服务器 IP 地址
     * @param port     FTP 服务器端口号
     * @param username 登录用户名
     * @param password 登录密码
     * @return
     * @throws Exception
     */
    public static FTPClient connectFtpServer(String addr, int port, String username, String password, String controlEncoding) {
        FTPClient ftpClient = new FTPClient();
        try {
            /**设置文件传输的编码*/
            ftpClient.setControlEncoding(controlEncoding);

            /**连接 FTP 服务器
             * 如果连接失败，则此时抛出异常，如ftp服务器服务关闭时，抛出异常：
             * java.net.ConnectException: Connection refused: connect*/
            ftpClient.connect(addr, port);
            /**登录 FTP 服务器
             * 1）如果传入的账号为空，则使用匿名登录，此时账号使用 "Anonymous"，密码为空即可*/
            if (StringUtils.isBlank(username)) {
                ftpClient.login("Anonymous", "");
            } else {
                ftpClient.login(username, password);
            }

            /** 设置传输的文件类型
             * BINARY_FILE_TYPE：二进制文件类型
             * ASCII_FILE_TYPE：ASCII传输方式，这是默认的方式
             * ....
             */
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

            /**
             * 确认应答状态码是否正确完成响应
             * 凡是 2开头的 isPositiveCompletion 都会返回 true，因为它底层判断是：
             * return (reply >= 200 && reply < 300);
             */
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                /**
                 * 如果 FTP 服务器响应错误 中断传输、断开连接
                 * abort：中断文件正在进行的文件传输，成功时返回 true,否则返回 false
                 * disconnect：断开与服务器的连接，并恢复默认参数值
                 */
                ftpClient.abort();
                ftpClient.disconnect();
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(">>>>>FTP服务器连接登录失败，请检查连接参数是否正确，或者网络是否通畅*********");
        }
        return ftpClient;
    }

    /**
     * 使用完毕，应该及时关闭连接
     * 终止 ftp 传输
     * 断开 ftp 连接
     *
     * @param ftpClient
     * @return
     */
    public static FTPClient closeFTPConnect(FTPClient ftpClient) {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.abort();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }


    /**
     * 下载单个文件
     * 下载 FTP 服务器上指定的单个文件，而且本地存放的文件相对部分路径 会与 FTP 服务器结构保持一致
     *
     * @param ftpClient              ：连接成功有效的 FTP客户端连接
     * @param absoluteLocalDirectory ：本地存储文件的绝对路径，如 E:\gxg\ftpDownload
     * @param relativeRemotePath     ：ftpFile 文件在服务器所在的绝对路径，此方法强制路径使用右斜杠"\"，如 "\video\2018.mp4"
     * @return
     */
    public static void downloadSingleFile(FTPClient ftpClient, String absoluteLocalDirectory, String relativeRemotePath) {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            System.out.println(">>>>>FTP服务器连接已经关闭或者连接无效*********");
            return;
        }
        if (StringUtils.isBlank(absoluteLocalDirectory) || StringUtils.isBlank(relativeRemotePath)) {
            System.out.println(">>>>>下载时遇到本地存储路径或者ftp服务器文件路径为空，放弃...*********");
            return;
        }
        try {
            /**没有对应路径时，FTPFile[] 大小为0，不会为null*/
            FTPFile[] ftpFiles = ftpClient.listFiles(relativeRemotePath);
            FTPFile ftpFile = null;
            if (ftpFiles.length >= 1) {
                ftpFile = ftpFiles[0];
            }
            if (ftpFile != null && ftpFile.isFile()) {
                /** ftpFile.getName():获取的是文件名称，如 123.mp4
                 * 必须保证文件存放的父目录必须存在，否则 retrieveFile 保存文件时报错
                 */
                File localFile = new File(absoluteLocalDirectory, relativeRemotePath);
                if (!localFile.getParentFile().exists()) {
                    localFile.getParentFile().mkdirs();
                }
                OutputStream outputStream = new FileOutputStream(localFile);
                String workDir = relativeRemotePath.substring(0, relativeRemotePath.lastIndexOf("\\"));
                if (StringUtils.isBlank(workDir)) {
                    workDir = "/";
                }
                /**文件下载前，FTPClient工作目录必须切换到文件所在的目录，否则下载失败
                 * "/" 表示用户根目录*/
                ftpClient.changeWorkingDirectory(workDir);
                /**下载指定的 FTP 文件 到本地
                 * 1)注意只能是文件，不能直接下载整个目录
                 * 2)如果文件本地已经存在，默认会重新下载
                 * 3)下载文件之前，ftpClient 工作目录必须是下载文件所在的目录
                 * 4)下载成功返回 true，失败返回 false
                 */
                ftpClient.retrieveFile(ftpFile.getName(), outputStream);

                outputStream.flush();
                outputStream.close();
                System.out.println(">>>>>FTP服务器文件下载完毕*********" + ftpFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载整个目录
     * 遍历 FTP 服务器指定目录下的所有文件(包含子孙文件)
     *
     * @param ftpClient        ：连接成功有效的 FTP客户端连接
     * @param remotePath       ：查询的 FTP 服务器目录，如果文件，则视为无效，使用绝对路径，如"/"、"/video"、"\\"、"\\video"
     * @param relativePathList ：返回查询结果，其中为服务器目录下的文件相对路径，如：\1.png、\docs\overview-tree.html 等
     * @return
     */
    public static List<String> loopServerPath(FTPClient ftpClient, String remotePath, List<String> relativePathList) {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            System.out.println("ftp 连接已经关闭或者连接无效......");
            return relativePathList;
        }
        try {
            /**转移到FTP服务器根目录下的指定子目录
             * 1)"/"：表示用户的根目录，为空时表示不变更
             * 2)参数必须是目录，当是文件时改变路径无效
             * */
            ftpClient.changeWorkingDirectory(remotePath);
            /** listFiles：获取FtpClient连接的当前下的一级文件列表(包括子目录)
             * 1）FTPFile[] ftpFiles = ftpClient.listFiles("/docs/info");
             *      获取服务器指定目录下的子文件列表(包括子目录)，以 FTP 登录用户的根目录为基准，与 FTPClient 当前连接目录无关
             * 2）FTPFile[] ftpFiles = ftpClient.listFiles("/docs/info/springmvc.txt");
             *      获取服务器指定文件，此时如果文件存在时，则 FTPFile[] 大小为 1，就是此文件
             * */
            FTPFile[] ftpFiles = ftpClient.listFiles();
            if (ftpFiles != null && ftpFiles.length > 0) {
                for (FTPFile ftpFile : ftpFiles) {
                    if (ftpFile.isFile()) {
                        String relativeRemotePath = remotePath + "\\" + ftpFile.getName();
                        relativePathList.add(relativeRemotePath);
                    } else {
                        loopServerPath(ftpClient, remotePath + "\\" + ftpFile.getName(), relativePathList);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativePathList;
    }

    //改变目录路径
    public static boolean changeWorkingDirectory(FTPClient ftpClient,String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                System.out.println("进入文件夹" + directory + " 成功！");
            } else {
                System.out.println("进入文件夹" + directory + " 失败！");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }


    //创建目录
    public static boolean makeDirectory(FTPClient ftpClient,String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                System.out.println("创建文件夹" + dir + " 成功！");
//                logger.debug("创建文件夹" + dir + " 成功！");

            } else {
                System.out.println("创建文件夹" + dir + " 失败！");
//
//                logger.debug("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //判断ftp服务器文件是否存在
    public static boolean existFile(FTPClient ftpClient,String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public static boolean CreateDirecroty(FTPClient ftpClient,String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
//        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(ftpClient,new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {

//                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                String subDirectory = new String(remote.substring(start, end));
                path = path + "/" + subDirectory;
                if (!existFile(ftpClient,path)) {
                    if (makeDirectory(ftpClient,subDirectory)) {
                        changeWorkingDirectory(ftpClient,subDirectory);
                    } else {
                        System.out.println("创建目录[" + subDirectory + "]失败");
//                        logger.debug("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(ftpClient,subDirectory);
                    }
                } else {
                    changeWorkingDirectory(ftpClient,subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }


    /**
     * 上传本地文件 或 目录
     * 至 FTP 服务器----保持 FTP 服务器与本地 文件目录结构一致
     *   ftp服务器一定要给与连接用户的写入权限
     *   同时 上传多个文件夹,或者文件,记得一次一次的上传,连接一次上传一次,关闭后再重复上述操作
     *
     * @param ftpClient  连接成功有效的 FTPClinet
     * @param uploadFile 待上传的文件 或 文件夹(此时会遍历逐个上传)
     * @throws Exception
     */
    public static Map uploadFiles(FTPClient ftpClient, File uploadFile, Ftp ftp, Machine machine) {
        ftpClient.enterLocalPassiveMode();
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        Map map=new HashMap();
        map.put("success",false);
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            System.out.println(">>>>>FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            map.put("message","FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return map;
        }
        if (uploadFile == null && !uploadFile.exists()) {
            System.out.println(">>>>>待上传文件为空或者文件不存在*****放弃文件上传****");
            map.put("message","待上传文件为空或者文件不存在*****放弃文件上传****");
            return map;
        }
        try {
            if (uploadFile.isDirectory()) {
                /**如果被上传的是目录时
                 * makeDirectory：在 FTP 上创建目录(方法执行完，服务器就会创建好目录，如果目录本身已经存在，则不会再创建)
                 * 1）可以是相对路径，即不以"/"开头，相对的是 FTPClient 当前的工作路径，如 "video"、"视频" 等，会在当前工作目录进行新建目录
                 * 2）可以是绝对路径，即以"/"开头，与 FTPCLient 当前工作目录无关，如 "/images"、"/images/2018"
                 * 3）注意多级目录时，必须确保父目录存在，否则创建失败，
                 *      如 "video/201808"、"/images/2018" ，如果 父目录 video与images不存在，则创建失败
                 * */
                ftpClient.makeDirectory(uploadFile.getName());
                /**变更 FTPClient 工作目录到新目录
                 * 1)不以"/"开头表示相对路径，新目录以当前工作目录为基准，即当前工作目录下不存在此新目录时，变更失败
                 * 2)参数必须是目录，当是文件时改变路径无效*/
                ftpClient.changeWorkingDirectory(uploadFile.getName());

                File[] listFiles = uploadFile.listFiles();
                for (int i = 0; i < listFiles.length; i++) {
                    File loopFile = listFiles[i];
                    if (loopFile.isDirectory()) {
                        /**如果有子目录，则迭代调用方法进行上传*/
                        uploadFiles(ftpClient, loopFile,ftp,machine);
                        /**changeToParentDirectory：将 FTPClient 工作目录移到上一层
                         * 这一步细节很关键，子目录上传完成后，必须将工作目录返回上一层，否则容易导致文件上传后，目录不一致
                         * */
                        ftpClient.changeToParentDirectory();
                    } else {
                        /**如果目录中全是文件，则直接上传*/
                        FileInputStream input = new FileInputStream(loopFile);
                        ftpClient.storeFile(loopFile.getName(), input);
                        input.close();
                        System.out.println(">>>>>文件上传成功****" + loopFile.getPath());
                        map.put("success",true);
                        return map;
                    }
                }
            } else {
                /**变更 FTPClient 工作目录到新目录
                 * 1)不以"/"开头表示相对路径，新目录以当前工作目录为基准，即当前工作目录下不存在此新目录时，变更失败
                 * 2)参数必须是目录，当是文件时改变路径无效*/
                String remote=ftp.getRemotepath()+'\\'+machine.getMachineName();

                if (FTPUtil.changeWorkingDirectory(ftpClient,remote)){//目录存在时
                    /**如果被上传的是文件时*/
                    FileInputStream input = new FileInputStream(uploadFile);
                    /** storeFile:将本地文件上传到服务器
                     * 1）如果服务器已经存在此文件，则不会重新覆盖,即不会再重新上传
                     * 2）如果当前连接FTP服务器的用户没有写入的权限，则不会上传成功，但是也不会报错抛异常
                     * */
//                    String remotePath=ftp.getRemotepath()+machine.getMachineName()+'\\'+uploadFile.getName();
                    String remotePath=uploadFile.getName();
                    ftpClient.storeFile(remotePath, input);
                    input.close();
                    System.out.println(">>>>>文件上传成功****" + uploadFile.getPath());
                    map.put("success",true);
                    return map;
                }else{
                    FTPUtil.makeDirectory(ftpClient,remote);
                    uploadFiles(ftpClient, uploadFile,ftp,machine);
                    map.put("success",true);
                    return map;
                }



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 上传本地文件 或 目录
     * 至 FTP 服务器----保持 FTP 服务器与本地 文件目录结构一致
     *   ftp服务器一定要给与连接用户的写入权限
     *   同时 上传多个文件夹,或者文件,记得一次一次的上传,连接一次上传一次,关闭后再重复上述操作
     *
     * @param ftpClient  连接成功有效的 FTPClinet
     * @param uploadFile 待上传的文件 或 文件夹(此时会遍历逐个上传)
     * @throws Exception
     */
    public static Map uploadMachineModelFiles(FTPClient ftpClient, File uploadFile, Ftp ftp, MachineModel machineModel) {
        ftpClient.enterLocalPassiveMode();
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        Map map=new HashMap();
        map.put("success",false);
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            System.out.println(">>>>>FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            map.put("message","FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return map;
        }
        if (uploadFile == null && !uploadFile.exists()) {
            System.out.println(">>>>>待上传文件为空或者文件不存在*****放弃文件上传****");
            map.put("message","待上传文件为空或者文件不存在*****放弃文件上传****");
            return map;
        }
        try {
            if (uploadFile.isDirectory()) {
                /**如果被上传的是目录时
                 * makeDirectory：在 FTP 上创建目录(方法执行完，服务器就会创建好目录，如果目录本身已经存在，则不会再创建)
                 * 1）可以是相对路径，即不以"/"开头，相对的是 FTPClient 当前的工作路径，如 "video"、"视频" 等，会在当前工作目录进行新建目录
                 * 2）可以是绝对路径，即以"/"开头，与 FTPCLient 当前工作目录无关，如 "/images"、"/images/2018"
                 * 3）注意多级目录时，必须确保父目录存在，否则创建失败，
                 *      如 "video/201808"、"/images/2018" ，如果 父目录 video与images不存在，则创建失败
                 * */
                ftpClient.makeDirectory(uploadFile.getName());
                /**变更 FTPClient 工作目录到新目录
                 * 1)不以"/"开头表示相对路径，新目录以当前工作目录为基准，即当前工作目录下不存在此新目录时，变更失败
                 * 2)参数必须是目录，当是文件时改变路径无效*/
                ftpClient.changeWorkingDirectory(uploadFile.getName());

                File[] listFiles = uploadFile.listFiles();
                for (int i = 0; i < listFiles.length; i++) {
                    File loopFile = listFiles[i];
                    if (loopFile.isDirectory()) {
                        /**如果有子目录，则迭代调用方法进行上传*/
                        uploadMachineModelFiles(ftpClient, loopFile,ftp,machineModel);
                        /**changeToParentDirectory：将 FTPClient 工作目录移到上一层
                         * 这一步细节很关键，子目录上传完成后，必须将工作目录返回上一层，否则容易导致文件上传后，目录不一致
                         * */
                        ftpClient.changeToParentDirectory();
                    } else {
                        /**如果目录中全是文件，则直接上传*/
                        FileInputStream input = new FileInputStream(loopFile);
                        ftpClient.storeFile(loopFile.getName(), input);
                        input.close();
                        System.out.println(">>>>>文件上传成功****" + loopFile.getPath());
                        map.put("success",true);
                        return map;
                    }
                }
            } else {
                /**变更 FTPClient 工作目录到新目录
                 * 1)不以"/"开头表示相对路径，新目录以当前工作目录为基准，即当前工作目录下不存在此新目录时，变更失败
                 * 2)参数必须是目录，当是文件时改变路径无效*/
                //#\\JudgeModels\\机器ID或机器名称\\工序ID或工序I名称\\产品ID或产品名称\\模版版本号+原模版压缩包名称
//                String remote=ftp.getRemotepath()+'\\'+machineModel.getMachineModelName();
                String remote1=ftp.getRemotepath()+'\\'+machineModel.getOperation().getOperationName();
                String remote2=ftp.getRemotepath()+'\\'+machineModel.getOperation().getOperationName()+'\\'+machineModel.getMachine().getMachineName();
                String remote3=ftp.getRemotepath()+'\\'+machineModel.getOperation().getOperationName()+'\\'+machineModel.getMachine().getMachineName()+'\\'+machineModel.getProduct().getProductName();
                if (FTPUtil.changeWorkingDirectory(ftpClient,ftp.getRemotepath())){
                    if (FTPUtil.changeWorkingDirectory(ftpClient,machineModel.getOperation().getOperationName())){//目录存在时
                        if (FTPUtil.changeWorkingDirectory(ftpClient,machineModel.getMachine().getMachineName())){//目录存在时
                            if (FTPUtil.changeWorkingDirectory(ftpClient,machineModel.getProduct().getProductName())){//目录存在时
                                /**如果被上传的是文件时*/
                                FileInputStream input = new FileInputStream(uploadFile);
                                /** storeFile:将本地文件上传到服务器
                                 * 1）如果服务器已经存在此文件，则不会重新覆盖,即不会再重新上传
                                 * 2）如果当前连接FTP服务器的用户没有写入的权限，则不会上传成功，但是也不会报错抛异常
                                 * */
                                String remotePath=uploadFile.getName();
                                ftpClient.storeFile(remotePath, input);
                                input.close();
                                System.out.println(">>>>>文件上传成功****" + uploadFile.getPath());
                                map.put("success",true);
                                return map;
                            }else{
                                FTPUtil.makeDirectory(ftpClient,machineModel.getProduct().getProductName());
                                FTPUtil.changeWorkingDirectory(ftpClient,"../../../../");
                                uploadMachineModelFiles(ftpClient, uploadFile,ftp,machineModel);
                                map.put("success",true);
                                return map;
                            }
                        }else{
                            FTPUtil.makeDirectory(ftpClient,machineModel.getMachine().getMachineName());
                            FTPUtil.changeWorkingDirectory(ftpClient,"../../../");
                            uploadMachineModelFiles(ftpClient, uploadFile,ftp,machineModel);
                            map.put("success",true);
                            return map;
                        }

                    }else{
                        FTPUtil.makeDirectory(ftpClient,machineModel.getOperation().getOperationName());
                        FTPUtil.changeWorkingDirectory(ftpClient,"../../");
                        uploadMachineModelFiles(ftpClient, uploadFile,ftp,machineModel);
                        map.put("success",true);
                        return map;
                    }

                }else{
                    FTPUtil.makeDirectory(ftpClient,ftp.getRemotepath());
                    FTPUtil.changeWorkingDirectory(ftpClient,"../");
                    uploadMachineModelFiles(ftpClient, uploadFile,ftp,machineModel);
                    map.put("success",true);
                    return map;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 以FTP上面的内容为准,可以用于备份FTP整个目录,本地文件与FTP更新同步
     * 同步本地目录与 FTP 服务器目录
     * 1）约定：FTP 服务器有，而本地没有的，则下载下来；本地有，而ftp服务器没有的，则将本地多余的删除
     * 2）始终确保本地与 ftp 服务器内容一致
     * 2）让 FTP 服务器与 本地目录保持结构一致，如 服务器上是 /docs/overview-tree.html，则本地也是 localDir/docs/overview-tree.html
     *
     * @param ftpClient        连接成功有效的 FTPClinet
     * @param localSyncFileDir ：与 FTP 目录进行同步的本地目录
     */
    public static void syncLocalDir(FTPClient ftpClient, String localSyncFileDir) throws IOException {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable() || StringUtils.isBlank(localSyncFileDir)) {
            System.out.println(">>>>>FTP服务器连接已经关闭或者连接无效*********");
            return;
        }

        /** 获取本地存储目录下的文件*/
        Collection<File> fileCollection = FileWmxUtil.localListFiles(new File(localSyncFileDir));
        System.out.println(">>>>>本地存储目录共有文件数量*********" + fileCollection.size());

        /**获取 FTP 服务器下的相对路径*/
        List<String> relativePathList = new ArrayList<>();
        relativePathList = loopServerPath(ftpClient, "", relativePathList);
        System.out.println(">>>>>FTP 服务器端共有文件数量*********" + relativePathList.size());

        /**
         * 遍历 本地存储目录下的文件
         * 1）如果本地文件在 FTP 服务器上不存在，则删除它
         * 2）如果本地文件在 FTP 服务器上存在，则比较两种大小
         *    如果大小不一致，则重新下载
         */
        for (File localFile : fileCollection) {
            String localFilePath = localFile.getPath();
            String localFileSuffi = localFilePath.replace(localSyncFileDir, "");
            if (relativePathList.contains(localFileSuffi)) {
                /**本地此文件在 FTP 服务器存在
                 * 1)比较大小，如果本地文件与服务器文件大小一致，则跳过
                 * 2）如果大小不一致，则删除本地文件，重新下载
                 * 3）最后都要删除relativePathList中的此元素，减轻后一次循环的压力*/
                FTPFile[] ftpFiles = ftpClient.listFiles(localFileSuffi);
                System.out.println(">>>>>本地文件 在 FTP 服务器已存在*********" + localFile.getPath());
                if (ftpFiles.length >= 1 && localFile.length() != ftpFiles[0].getSize()) {
                    downloadSingleFile(ftpClient, localSyncFileDir, localFileSuffi);
                    System.out.println(">>>>>本地文件与 FTP 服务器文件大小不一致，重新下载*********" + localFile.getPath());
                }
                relativePathList.remove(localFileSuffi);
            } else {
                System.out.println(">>>>>本地文件在 FTP 服务器不存在，删除本地文件*********" + localFile.getPath());
                /**本地此文件在 FTP 服务器不存在
                 * 1)删除本地文件
                 * 2）如果当前文件所在目录下文件已经为空，则将此父目录也一并删除*/
                localFile.delete();
                File parentFile = localFile.getParentFile();
                while (parentFile.list().length == 0) {
                    parentFile.delete();
                    parentFile = parentFile.getParentFile();
                }
            }
        }
        for (int i = 0; i < relativePathList.size(); i++) {
            System.out.println(">>>>> FTP 服务器存在新文件，准备下载*********" + relativePathList.get(i));
            downloadSingleFile(ftpClient, localSyncFileDir, relativePathList.get(i));
        }
    }



    /**
     * 删除服务器的文件,或者目录
     *
     * @param ftpClient   连接成功且有效的 FTP客户端
     * @param deleteFiles 待删除的文件或者目录，为目录时，会逐个删除，
     *                    路径必须是绝对路径，如 "/1.png"、"/video/3.mp4"、"/images/2018"
     *                    "/" 表示用户根目录,则删除所有内容
     */
    public static void deleteServerFiles(FTPClient ftpClient, String deleteFiles) {
        /**如果 FTP 连接已经关闭，或者连接无效，则直接返回*/
        if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
            System.out.println(">>>>>FTP服务器连接已经关闭或者连接无效*****放弃文件上传****");
            return;
        }
        try {
            /** 尝试改变当前工作目录到 deleteFiles
             * 1）changeWorkingDirectory：变更FTPClient当前工作目录，变更成功返回true，否则失败返回false
             * 2）如果变更工作目录成功，则表示 deleteFiles 为服务器已经存在的目录
             * 3）否则变更失败，则认为 deleteFiles 是文件，是文件时则直接删除
             */
            boolean changeFlag = ftpClient.changeWorkingDirectory(deleteFiles);
            if (changeFlag) {
                /**当被删除的是目录时*/
                FTPFile[] ftpFiles = ftpClient.listFiles();
                for (FTPFile ftpFile : ftpFiles) {
                    System.out.println("----------------::::" + ftpClient.printWorkingDirectory());
                    if (ftpFile.isFile()) {
                        boolean deleteFlag = ftpClient.deleteFile(ftpFile.getName());
                        if (deleteFlag) {
                            System.out.println(">>>>>删除服务器文件成功****" + ftpFile.getName());
                        } else {
                            System.out.println(">>>>>删除服务器文件失败****" + ftpFile.getName());
                        }
                    } else {
                        /**printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                         * 然后开始迭代删除子目录
                         */
                        String workingDirectory = ftpClient.printWorkingDirectory();
                        deleteServerFiles(ftpClient, workingDirectory + "/" + ftpFile.getName());
                    }
                }
                /**printWorkingDirectory：获取 FTPClient 客户端当前工作目录
                 * removeDirectory：删除FTP服务端的空目录，注意如果目录下存在子文件或者子目录，则删除失败
                 * 运行到这里表示目录下的内容已经删除完毕，此时再删除当前的为空的目录，同时将工作目录移动到上移层级
                 * */
                String workingDirectory = ftpClient.printWorkingDirectory();
                ftpClient.removeDirectory(workingDirectory);
                ftpClient.changeToParentDirectory();
            } else {
                /**deleteFile：删除FTP服务器上的文件
                 * 1）只用于删除文件而不是目录，删除成功时，返回 true
                 * 2）删除目录时无效,方法返回 false
                 * 3）待删除文件不存在时，删除失败，返回 false
                 * */
                boolean deleteFlag = ftpClient.deleteFile(deleteFiles);
                if (deleteFlag) {
                    System.out.println(">>>>>删除服务器文件成功****" + deleteFiles);
                } else {//删除失败原因为,没有这个文件
                    System.out.println(">>>>>删除服务器文件失败****" + deleteFiles);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/*
    //测试连接关闭
    public static void main(String[] args) throws Exception {
        System.out.println("-----------------------应用启动------------------------");
        FTPClient ftpClient = FTPUtil.connectFtpServer("192.168.137.200", 21, "ftpuser", "ftpuser", "GBK");
        System.out.println("FTP 连接是否成功：" + ftpClient.isConnected());
        System.out.println("FTP 连接是否有效：" + ftpClient.isAvailable());
        closeFTPConnect(ftpClient);
        System.out.println("-----------------------应用关闭------------------------");
    }*/
    @Autowired
    Ftp ftp;
    //测试单个文件下载,与单个文件下载,支持中文
    public static void main(String[] args) throws Exception {
        System.out.println("-----------------------应用启动------------------------");
        FTPClient ftpClient = FTPUtil.connectFtpServer("192.168.137.200", 21, "ftpuser", "ftpuser", "GBK");
        downloadSingleFile(ftpClient, "D:\\FTPceshi", "\\中文.txt");
        downloadSingleFile(ftpClient, "D:\\FTPceshi", "\\wenjianjia\\中文.txt");
        closeFTPConnect(ftpClient);
        System.out.println("-----------------------应用关闭------------------------");
    }
/*
    //测试下载一个目录及其文件
    public static void main(String[] args) throws Exception {

        System.out.println("-----------------------应用启动------------------------");
        FTPClient ftpClient = FTPUtil.connectFtpServer("192.168.137.200", 21, "ftpuser", "ftpuser", "GBK");

        List<String> relativePathList = new ArrayList<>();

        relativePathList = loopServerPath(ftpClient, "\\ftpDownload", relativePathList);
        for (String relativePath : relativePathList) {
            System.out.println("准备下载的服务器文件：" + relativePath);
            downloadSingleFile(ftpClient, "D:\\FTPceshi", relativePath);
        }


        closeFTPConnect(ftpClient);
        System.out.println("-----------------------应用关闭------------------------");
    }*/

    //测试上传文件夹或者文件
    //ftp服务器一定要给与连接用户的写入权限
    //上传多个文件夹,或者文件,记得一次一次的上传,连接一次上传一次,关闭后再重复上述操作
//    public static void main(String[] args) throws Exception {
//
//       /* System.out.println("-----------------------应用启动------------------------");
//        FTPClient ftpClient01 = FTPUtil.connectFtpServer("192.168.137.200", 21, "ftpuser", "ftpuser", "GBK");
//
////        uploadFiles(ftpClient, new File("D:\\FTPceshi\\uploadFiles"));
//        uploadFiles(ftpClient01, new File("D:\\FTPceshi\\uploadFiles"));
//        closeFTPConnect(ftpClient01);
//*//*
//        FTPClient ftpClient02 = FTPUtil.connectFtpServer("192.168.137.200", 21, "ftpuser", "ftpuser", "GBK");
//        uploadFiles(ftpClient02, new File("D:\\FTPceshi\\中文.txt"));
//        closeFTPConnect(ftpClient02);*//*
//
//        System.out.println("-----------------------应用关闭------------------------");*/
//    }

/*
    //测试:同步ftp服务器目录到本地的某个目录下,相当于备份整个ftp目录
    public static void main(String[] args) throws Exception {

        System.out.println("-----------------------应用启动------------------------");
        FTPClient ftpClient = FTPUtil.connectFtpServer("192.168.137.200", 21, "ftpuser", "ftpuser", "GBK");

        syncLocalDir(ftpClient,"D:\\FTPceshi");//同步到本地的这个目录

        closeFTPConnect(ftpClient);
        System.out.println("-----------------------应用关闭------------------------");
    }*/

/*
    //测试:删除文件或者目录
    public static void main(String[] args) throws Exception {

        System.out.println("-----------------------应用启动------------------------");
        FTPClient ftpClient = FTPUtil.connectFtpServer("192.168.137.200", 21, "ftpuser", "ftpuser", "GBK");
        deleteServerFiles(ftpClient,"/1.txt");//删文件
        deleteServerFiles(ftpClient,"/uploadFiles");//删目录
        closeFTPConnect(ftpClient);
        System.out.println("-----------------------应用关闭------------------------");
    }
*/



}