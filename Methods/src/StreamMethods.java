
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 流方法
 */
public class StreamMethods {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        Collections.addAll(list1, "abc", "def", "ghi", "jkl");
        Stream<String> stream1 = list1.stream();
        stream1.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        System.out.println();
        list1.stream().forEach(s -> System.out.println(s));

    }
    //https://developer.aliyun.com/article/1609988
}
