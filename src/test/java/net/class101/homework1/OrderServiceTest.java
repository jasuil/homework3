package net.class101.homework1;

import lombok.extern.slf4j.Slf4j;

import net.class101.homework1.data.bean.CartBean;
import net.class101.homework1.data.entity.Product;
import net.class101.homework1.data.repository.ProductRepository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
//@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService testClass;
    @Mock
    ProductRepository productRepository;
    private final ByteArrayOutputStream outputStreamCaptor;

    public OrderServiceTest() {
        outputStreamCaptor = new ByteArrayOutputStream();
    }

    public void printOutSetUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /**
     * 결제금액 표시에 대한 메서드 테스트
     * expect: 주문비용과 지불금액이 기대값과 일치하는가
     */
    @Test
    public void totalSumTest() {
        testClass = new OrderService();

        this.printOutSetUp();
        List<CartBean.OrderBean> orderList = new ArrayList<>();
        CartBean cart = new CartBean();
        CartBean.OrderBean order = cart.new OrderBean();
        order.setAmount(10);
        order.setPrice(4000);
        order.setCategory("KIT");
        orderList.add(order);

        try {
            Method testMethod = testClass.getClass().getDeclaredMethod("totalSum", List.class);
            testMethod.setAccessible(true);
            testMethod.invoke(testClass, orderList);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

        Assert.assertEquals("주문 비용 : 40,000\n" +
                "--------------------------------\n" +
                "지불 금액 : 45,000\n" +
                "--------------------------------\n",
                outputStreamCaptor.toString().replaceAll("\r", ""));
    }

    @Test
    public void test() {

        List<CartBean.OrderBean> orderBeanList = new ArrayList<>();

        CartBean cartBean = new CartBean();
        CartBean.OrderBean orderBean = cartBean.new OrderBean();
        orderBean.setName("this is not klass");
        orderBean.setCategory("KIT");
        orderBean.setId(1);
        orderBean.setAmount(2);
        orderBeanList.add(orderBean);

        orderBean = cartBean.new OrderBean();
        orderBean.setName("this is not klass");
        orderBean.setCategory("KIT");
        orderBean.setId(4);
        orderBean.setAmount(32);
        orderBeanList.add(orderBean);

        orderBean = cartBean.new OrderBean();
        orderBean.setName("this is klass");
        orderBean.setCategory("KLASS");
        orderBean.setId(2);
        orderBean.setAmount(22);
        orderBeanList.add(orderBean);

        orderBean = cartBean.new OrderBean();
        orderBean.setName("this is klass");
        orderBean.setCategory("KLASS");
        orderBean.setId(3);
        orderBean.setAmount(23);
        orderBeanList.add(orderBean);


        Map<String, List<CartBean.OrderBean>> map1 = orderBeanList.stream().collect(Collectors.groupingBy(CartBean.OrderBean::getName));
        Map<String, List<CartBean.OrderBean>> map2 =null;

        List<Map<String, List<CartBean.OrderBean>>> list = new ArrayList<>();
                Iterator<String> keys = map1.keySet().iterator();
        while (keys.hasNext()){
            String key = keys.next();

            map2= map1.get(key).stream().collect(Collectors.groupingBy(CartBean.OrderBean::getCategory));
            list.add(map2);
        }

        System.out.println(map2.size());
    }
}
