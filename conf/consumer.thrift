 namespace java service.demo
 struct Task {
    1:required i32 id;
    2:required string name;
    3:required i32 templateId;
    4:required list<string> urls;
    5:required i32 proxy;
    6:required i32 javascript
    7:required i32 threadNum;
    8:required string template;
    9:required i32 templateType;
 }

 service ConsumerThrift{
  bool has(1:string url)
  void done(1:string node)
  void statistic(1:string statistic)
  list<Task> getTasks()
 }