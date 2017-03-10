package zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

public class zkHelper {
	// 会话超时时间，设置为与系统默认时间一致
	private static int timeout = 30000;
	private static String port="master:2181";
	private static List<ACL> acl=Ids.OPEN_ACL_UNSAFE;
	private static CreateMode createMode=CreateMode.PERSISTENT;
	
	// 创建 ZooKeeper 实例
	ZooKeeper zk;
	// 创建 Watcher 实例
	Watcher wh = new Watcher() {
		public void process(org.apache.zookeeper.WatchedEvent event) {
			System.out.println(event.toString());
		}
	};

	/**
	 * 初始化 ZooKeeper 实例
	 * @param port
	 * @param timeout
	 * @throws IOException
	 */
	public void init(String port,int timeout) throws IOException {
		if(null != zk){
		return ;
		}
		zk = new ZooKeeper(port, timeout, this.wh);
	}
	public void init() throws IOException {
		if(null != zk){
		return ;
		}
		zk = new ZooKeeper(port, timeout, this.wh);
	}
    /**
     * 创建一个znode
     * @param path
     * @param data
     * @param acl
     * @param createMode
     * @throws KeeperException
     * @throws InterruptedException
     */
	public void Create(String path,byte[] data,List<ACL> acl,CreateMode createMode) throws KeeperException, InterruptedException{
		if(isExits(path)==false){
		zkHelper.acl=acl;
		zkHelper.createMode=createMode;
		zk.create(path, data, acl, createMode);
		}
	}
	/**
	 * 创建一个znode
	 * @param path
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void CreateZnode(String path,byte[] data) throws KeeperException, InterruptedException{
		if(isExits(path)==false){
		zk.create(path, data, acl, createMode);
		}
	}
	/**
	 *获取某个znode的信息
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public byte[] GetData(String path) throws KeeperException, InterruptedException{
		if(isExits(path)==false){
			System.out.println("该节点不存在，请先创建");
		}
		return zk.getData(path, false, null);
		
	}
	/**
	 * 修改某个znode的信息
	 * @param path
	 * @param data
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public byte[] SetData(String path,String data) throws KeeperException, InterruptedException{
		if(isExits(path)==true){
		zk.setData(path,data.getBytes(),-1);
		}
		return zk.getData(path, false, null);
	}
	/**
	 * 删除某个znode
	 * @param path
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public void DeleteZnode(String path) throws InterruptedException, KeeperException{
		if(isExits(path)==true){
			zk.delete(path, -1);
		}
		
		
	}
	/**
	 * 查看该节点是否存在
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public boolean isExits(String path) throws KeeperException, InterruptedException{
		if(null !=zk.exists(path, false)){
			return true;
		}
		return false;
		
	}
		
	public void uninit() throws InterruptedException{
		zk.close();
	}
	

	
}