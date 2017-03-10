package zookeeper;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

public class Test {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
    zkHelper zk=new zkHelper();
    zk.init("master:2181", 30000);
    //创建一个znode
	//zk.CreateZnode("/zoo3", "hello".getBytes());
    //查看该节点是否存在
    zk.GetData("/ss");
	}

}
