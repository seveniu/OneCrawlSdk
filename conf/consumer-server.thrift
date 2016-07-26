 namespace java com.seveniu.thrift
 enum TaskStatus { // 其实使用 com.seveniu.def.TaskStatus
    WAIT,RUNNING,STOP,FAIL
 }
 service ConsumerThrift{
  bool has(1:string url)
  void done(1:string node)
  void statistic(1:string statistic)
  void taskStatusChange(1:string taskId,2:TaskStatus status)
 }