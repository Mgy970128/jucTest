package top.mgy.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 链式编程 + 流式编程
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ChainDemo {

    private int id;
    private String bookName;
    private double price;

    public static void main(String[] args) {

        ChainDemo demo = new ChainDemo();
        demo.setId(20).setBookName("平凡的世界").setPrice(65.2);

    }
}
