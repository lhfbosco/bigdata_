package fileWatch;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyWatchService {

    public static void main(String[] args) throws Exception {
    	//启动监听器
    	SBManager m = new SBManager();
    	//初始化bucket的内容，使bucket与本地目录的文件同步
    	m.Init();
    	//要监控的文件目录，注意要多加一个 "/"
        String propFileName = "F:\\其他资料\\sync\\"; 
        WatchService watchService= FileSystems.getDefault().newWatchService();
        Path path = Paths.get(propFileName);
        path.register(watchService, 
        		StandardWatchEventKinds.ENTRY_CREATE, 
        		StandardWatchEventKinds.ENTRY_DELETE, 
                StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey key;
    	//记录操作的次数
    	int count = 0;
        while ((key = watchService.take()) != null) {
        	
        	for (WatchEvent<?> event : key.pollEvents()) {
        		if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
        			count++;
        			System.out.println("count"+count);
        			System.out.println("本地目录的 " + event.context() + " 创建");
        			m.Upload(event.context() + " ");
            	}
            	if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
            		count++;
        			System.out.println("count"+count);
        			System.out.println("本地目录的 " + event.context()+ " 删除");
        			m.Delete(event.context() + " ");
            			
            	}
            	if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
            		count++;
        			System.out.println("count"+count);
        			System.out.println("本地目录的 " + event.context() + " 修改");
        			m.Modify_(event.context() + " ");
            			
            	}
          }
        	key.reset();
      }
    }

}
