						程序的执行流程
1、首先执行TransmissionInit.java的中main方法，从而启动服务端。
2、执行FileDispath.java中的main方法，把c:\1.txt下的文件分发到E:\1.txt(在FileServerHandler中设置的)
3、上传文件的代码在TcpSyncServiceImpl.upload()。执行完session.write()后，会触发FileServerHandler的
messageReceived方法，首先Server端会验证文件的状态，然后也会调用session.write(sendFileMsg);这样触发
FileClientHandler的messageReceived,从而使客户端开始读取本地要分发的文件(ReadTempFile),执行完以后继续回到服务端，
触发写文件的操作(WriteTempFile)。服务端写完以后把状态发回客户端,CommandList.RETURN_STATUS,客户端通过这个状态
来判断分发是否成功

