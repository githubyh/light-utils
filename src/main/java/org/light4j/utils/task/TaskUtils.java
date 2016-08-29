package org.light4j.utils.task;

import java.util.List;

/**
 * Java���̡߳�����ʵ���������
 * 
 * @author longjiazuo
 */
public class TaskUtils {
	
	
    /**
     * ����첽����(�����б�)
     * @param taskList
     */
	public static void addTaskList(List<TaskEntity> taskList){
		TaskPoolManager.newInstance().addTasks(taskList);
	}
	
	 /**
     * ����첽����(��������)
     * @param taskList
     */
	public static void addTask(TaskEntity task){
		TaskPoolManager.newInstance().addTask(task);
	}
	
}
