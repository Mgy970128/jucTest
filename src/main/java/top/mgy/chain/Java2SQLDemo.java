package top.mgy.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
class User{
    private int id;
    private String userName;
    private int age;
}

public class Java2SQLDemo {

    public static void main(String[] args) {
        /**
         * 按照给出的数据,找出满足下列条件的用户
         * 偶数ID且年龄大于24，且用户名转为大写，用户名字母倒排序
         * 只输出用户名
         */

        User user1 = new User(11,"a",23);
        User user2 = new User(12,"b",24);
        User user3 = new User(13,"c",22);
        User user4 = new User(14,"d",28);
        User user5 = new User(16,"e",29);

        List<User> list = Arrays.asList(user1,user2,user3,user4,user5);

        List<String> collect = list.stream()
                .filter(user -> user.getId() % 2 == 0 && user.getAge() > 24)
                .map(user -> user.getUserName().toUpperCase())
                .sorted(Comparator.reverseOrder()).skip(1).limit(1)
                .collect(Collectors.toList());

        System.out.println(collect);


    }
}
