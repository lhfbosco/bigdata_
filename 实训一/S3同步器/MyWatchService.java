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
    	//����������
    	SBManager m = new SBManager();
    	//��ʼ��bucket�����ݣ�ʹbucket�뱾��Ŀ¼���ļ�ͬ��
    	m.Init();
    	//Ҫ��ص��ļ�Ŀ¼��ע��Ҫ���һ�� "/"
        String propFileName = "F:\\��������\\sync\\"; 
        WatchService watchService= FileSystems.getDefault().newWatchService();
        Path path = Paths.get(propFileName);
        path.register(watchService, 
        		StandardWatchEventKinds.ENTRY_CREATE, 
        		StandardWatchEventKinds.ENTRY_DELETE, 
                StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey key;
    	//��¼�����Ĵ���
    	int count = 0;
        while ((key = watchService.take()) != null) {
        	
        	for (WatchEvent<?> event : key.pollEvents()) {
        		if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
        			count++;
        			System.out.println("count"+count);
        			System.out.println("����Ŀ¼�� " + event.context() + " ����");
        			m.Upload(event.context() + " ");
            	}
            	if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
            		count++;
        			System.out.println("count"+count);
        			System.out.println("����Ŀ¼�� " + event.context()+ " ɾ��");
        			m.Delete(event.context() + " ");
            			
            	}
            	if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
            		count++;
        			System.out.println("count"+count);
        			System.out.println("����Ŀ¼�� " + event.context() + " �޸�");
        			m.Modify_(event.context() + " ");
            			
            	}
          }
        	key.reset();
      }
    }

}
