package com.taotao.manager.service;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;


public class MyTest {
    @Test
    public void  show () throws JMSException {
        //创建连接工厂对象 采用tcp协议
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory("tcp://192.168.32.136:61616");
        //创建连接对象
        Connection connection =connectionFactory.createConnection();
        connection.start();
        //有连接，创建会话对象
        ////第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        //参数：队列的名称。
        //使用点对点来发送消息到消息队列 名字为test-queue（key ：value）
        Queue queue = session.createQueue("test-queue");
        // 第六步：使用Session对象创建一个Producer对象。
        //这里就是明确是消费者还是生成者的地方
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = session.createTextMessage("zhe shi de er ci fa song activeMq");
        producer.send(textMessage);
        // 第九步：关闭资源。
        producer.close();
        session.close();
        connection.close();
        System.out.println("发送成功");
    }
    @Test
    public void  show1() throws Exception {
        //创建连接工厂对象 采用tcp协议
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory("tcp://192.168.32.136:61616");
        //创建连接对象
        Connection connection =connectionFactory.createConnection();
        connection.start();
        //有连接，创建会话对象
        ////第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        //参数：队列的名称。
        //使用点对点来发送消息到消息队列 名字为test-queue（key ：value）
        Queue queue = session.createQueue("test-queue");
        // 第六步：使用Session对象创建一个Producer对象。
        //这里就是明确是消费者还是生成者的地方
       // MessageProducer producer = session.createProducer(queue);
        MessageConsumer consumer = session.createConsumer(queue);
        System.out.println("--------------------");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println("aaaaaaa");
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer.close();
        session.close();
        connection.close();
        System.out.println("接收成功");
    }
    @Test
    public void  show2() throws Exception {
        //创建连接工厂对象 采用tcp协议
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory("tcp://192.168.32.136:61616");
        //创建连接对象
        Connection connection =connectionFactory.createConnection();
        connection.start();
        //有连接，创建会话对象
        ////第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        //参数：队列的名称。
        //使用点对点来发送消息到消息队列 名字为test-queue（key ：value）
       // Queue queue = session.createQueue("test-queue");
        Topic topic = session.createTopic("test-topic");
        // 第六步：使用Session对象创建一个Producer对象。
        //这里就是明确是消费者还是生成者的地方
         MessageProducer producer = session.createProducer(topic);
       // MessageConsumer consumer = session.createConsumer(queue);
        TextMessage textMessage = session.createTextMessage("hello activeMq,this is my topic test");
        // 第八步：使用Producer对象发送消息。
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
        System.out.println("发送成功");
    }
    @Test
    public void  show3() throws Exception {
        //创建连接工厂对象 采用tcp协议
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory("tcp://192.168.32.136:61616");
        //创建连接对象
        Connection connection =connectionFactory.createConnection();
        connection.start();
        //有连接，创建会话对象
        ////第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        //参数：队列的名称。
        //使用点对点来发送消息到消息队列 名字为test-queue（key ：value）
        //Queue queue = session.createQueue("test-queue");
        Topic topic = session.createTopic("test-topic");
        // 第六步：使用Session对象创建一个Producer对象。
        //这里就是明确是消费者还是生成者的地方
        // MessageProducer producer = session.createProducer(queue);
        MessageConsumer consumer = session.createConsumer(topic);
        System.out.println("--------------------");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
        System.out.println("接收成功");
    }
    @Test
    public void  show4() throws Exception {
        //创建连接工厂对象 采用tcp协议
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory("tcp://192.168.32.136:61616");
        //创建连接对象
        Connection connection =connectionFactory.createConnection();
        connection.start();
        //有连接，创建会话对象
        ////第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
        //第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        //参数：队列的名称。
        //使用点对点来发送消息到消息队列 名字为test-queue（key ：value）
        //Queue queue = session.createQueue("test-queue");
        Topic topic = session.createTopic("test-topic");
        // 第六步：使用Session对象创建一个Producer对象。
        //这里就是明确是消费者还是生成者的地方
        // MessageProducer producer = session.createProducer(queue);
        MessageConsumer consumer = session.createConsumer(topic);
        System.out.println("--------------------");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
        System.out.println("接收成功444444444444444");
    }
    @Test
    public void testQueueProducer() throws Exception {
        // 第一步：初始化一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 第二步：从容器中获得JMSTemplate对象。
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        // 第三步：从容器中获得一个Destination对象
        Queue queue = (Queue) applicationContext.getBean("queueDestination");
        // 第四步：使用JMSTemplate对象发送消息，需要知道Destination
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("spring activemq test");
                return textMessage;
            }
        });
    }
}
