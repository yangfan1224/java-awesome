package github.com.yangfan1224.awesome.concurrent.thread;

public class ChildThread extends Thread {
    private String name = null;

    public ChildThread(String name)
    {
        this.name = name;
    }

    @Override
    public void run()
    {
        System.out.println(this.name + "--child thead begin");

        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }

        System.out.println(this.name + "--child thead over");
    }
}
