 namespace java service.demo
 enum TaskStatus {
    WAIT,RUNNING,STOP
 }
 service ConsumerThrift{
  bool has(1:string url)
  void done(1:string node)
  void statistic(1:string statistic)
  void taskStatusChange(1:string taskId,2:TaskStatus status)
 }