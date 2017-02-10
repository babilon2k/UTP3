package zad1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

public class App extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	Object data;
	DefaultListModel<String> listModel = new DefaultListModel<String>();
	JList<String> list = new JList<String>(listModel);
	JTextArea textArea = new JTextArea(20, 20);
	public App()
	{

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);

		add(new JScrollPane(textArea));
		JPanel panel = new JPanel();
		JButton button = new JButton("Start");
		button.addActionListener(this);
		panel.add(button);
		button = new JButton("Get Result");
		button.setActionCommand("GetResult");
		button.addActionListener(this);
		panel.add(button);
		button = new JButton("Stop current");
		button.setActionCommand("Stop");
		button.addActionListener(this);
		panel.add(button);
		button = new JButton("Current result");
		button.setActionCommand("Result");
		button.addActionListener(this);
		panel.add(button);
		button = new JButton("Shutdown");
		button.addActionListener(this);
		panel.add(button);
		button = new JButton("Shutdown Now");
		button.setActionCommand("ShutdownNow");
		button.addActionListener(this);
		panel.add(button);
		panel.add(list);
		add(panel, "North");
		add(list, "South");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		try
		{
			Method m = this.getClass().getDeclaredMethod("task" + cmd);
			m.invoke(this);
		} catch (Exception exc)
		{
			exc.printStackTrace();
		}
	}

	Future<Integer> task;

	ExecutorService exec = Executors.newFixedThreadPool(3);

	public void taskStart()
	{
		int k = 0;
		int n = 15;
		try
		{

			task = exec.submit(new Task(++k, n));
		} catch (RejectedExecutionException exc)
		{
			textArea.append("Execution rejected\n");
			return;
		}
		listModel.addElement("Task " + k + " submitted\n");
	}

	public void taskGetResult()
	{
		String msg = "";
		if (task.isDone())
		{
			try
			{
				msg = "Ready. Result = " + task.get();
			} catch (Exception exc)
			{
				msg = exc.getMessage();
			}
			JOptionPane.showMessageDialog(null, msg);
		} else
		{
			msg = "No result yet! Try use \"Current result\" button.";
			JOptionPane.showMessageDialog(null, msg);
		}

	}
	public void taskResult()
	{
		String msg = "";
		if (task.isCancelled())
			msg = "Task cancelled.";
		else if (task.isDone())
		{
			try
			{
				msg = "Ready. Result = " + task.get();
			} catch (Exception exc)
			{
				msg = exc.getMessage();
			}
		} else
			msg = "Task is running or waiting for execution";
		JOptionPane.showMessageDialog(null, msg);
	}

	public void taskStop()
	{
		task.cancel(true);
	}

	public void taskShutdown()
	{
		exec.shutdown();
		textArea.append("Executor shutdown\n");
	}

	public void taskShutdownNow()
	{
		List<Runnable> awaiting = exec.shutdownNow();
		textArea.append("Eeecutor shutdown now - awaiting tasks:\n");
		for (Runnable r : awaiting)
		{
			textArea.append(r.getClass().getName() + '\n');
		}

	}
	public class Task implements Callable<Integer>
	{
		private int taskNum, limit;

		public Task(int taskNum, int limit)
		{
			super();
			this.taskNum = taskNum;
			this.limit = limit;
		}

		@Override
		public Integer call() throws Exception
		{
			int sum = 0;
			for (int i = 1; i <= limit; i++)
			{
				if (Thread.currentThread().isInterrupted())
					return null;
				sum += i;
				textArea.append(
						"Task " + taskNum + " part result = " + sum + '\n');

				System.out.println();
				Thread.sleep(1000);
			}
			return sum;
		}
	}
}
