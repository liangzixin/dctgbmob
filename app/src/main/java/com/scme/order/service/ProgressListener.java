package com.scme.order.service;

/**
 * 接收并处理服务器反馈数据的监听器
 * 
 * @author JianbinZhu
 *
 */
public interface ProgressListener {
	/**
	 * 回调原型
	 * 
	 * @param what 接受什么东西
	 * @param finish 完成了多少（100为完全）
	 */
	public void onProg(int what, int finish);
}
