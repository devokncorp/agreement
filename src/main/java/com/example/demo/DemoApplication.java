package com.example.demo;


import com.example.demo.core.api.IOvmRequest;
import com.example.demo.core.entity.Payment;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Component
@Slf4j
@Repository
class PostRepository {

    private static final List<Post> DATA = new ArrayList<>();

    static {
        for (int i = 0; i < 100; i++) {
            DATA.add(Post.builder().id(UUID.randomUUID()).title(String.valueOf(i)).build());

        }

    }
    public Payment getPa(Payment payment) {
        return payment;
    }

    public List<Payment> getPa(IOvmRequest req) {
        return new ArrayList<>();
    }


//    Flux<List<Post>> findAll() {
//
////        return  Flux.interval(Duration.ofSeconds(1)).map(l ->Post.builder().content(String.valueOf(Math.random())).build() );
//
//        List<Post> postList = Collections.synchronizedList(new ArrayList<>());
//
//
//        Flux<List<Post>> integerFlux = Flux.create((FluxSink<List<Post>> fluxSink) -> {
//
//            for (int i = 0; i < 5; i++) {
//                postList.add(Post.builder().content(String.valueOf(i)).build());
//                fluxSink.next(postList);
//            }
//
//            fluxSink.complete();
//        });
//
//        return integerFlux;

//        return Flux.interval(Duration.ofMillis(500)).map(l ->
//                get()
//        );

//        Flux<Post> generate = Flux.generate(sink -> {
//            StringBuilder sb = new StringBuilder();
//            do {
//                for (char c : "0123456789".toCharArray()) {
//                    sb.append(c);
//
//                        sink.next(Post.builder().content(sb.toString()).build());
//
//
//                }
//            } while (true);
//        });
//
//        return generate;
//        return Flux.fromIterable(DATA);
//    }

    public List<Post> get() {
        if (Math.floor(Math.random() * 10 % 2) == 0) {
            return Arrays.asList(getP("1"));
        } else {
            return Arrays.asList(getP("3"));
        }
    }

    private Post getP(String s) {
        return Post.builder().content(s).build();
    }


//    Mono<Post> findById(UUID id) {
////        return findAll().filter(p -> p.getId().equals(id)).last();
//        return null;
//    }

//    Mono<Post> save(Post post) {
//        return null;
////        Post saved = getP(Post.builder().id(UUID.randomUUID()).title(post.getTitle()), post.getContent());
////        log.debug("saved post: {}", saved);
////        DATA.add(saved);
////        return Mono.just(saved);
//    }

}

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Post {
    private UUID id;
    private String title;
    private String content;
}


class X {


    public static void giveRangeOfLongs_whenSummedInParallel_shouldBeEqualToExpectedTotal() throws InterruptedException, ExecutionException {

        long firstNum = 1;
        long lastNum = 1_000_000;

        List<Long> aList = LongStream.rangeClosed(firstNum, lastNum).boxed().collect(Collectors.toList());

        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        long actualTotal = customThreadPool.submit(() -> aList.parallelStream().reduce(0L, Long::sum)).get();

        System.out.println(actualTotal);
    }

    public static void main(String[] args) {
        List<Payment> sourceDbResults = new ArrayList<>();//Arrays.asList(1, 2, 3, 4, 5, 8);
        List<Payment> hiveResults = new ArrayList<>(); //Arrays.asList(2, 3, 6, 7);

        List<Payment> inSourceDb_notInHive = new ArrayList<>();
        List<Payment> inHive_notInSourceDb = new ArrayList<>();

        for (int i = 0; i < 5_000_000; i++) {
            int i1 = ((Double) Math.ceil(Math.random() * 10_000_000)).intValue();
            int i2 = ((Double) Math.ceil(Math.random() * 10)).intValue();

            sourceDbResults.add(Payment.builder().fileNo("qwdwwedwe" + String.valueOf(i1)).order(i).build());
            hiveResults.add(Payment.builder().fileNo("qwdwwedwe" + String.valueOf(i1)).order(i).build());
        }

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sourceDbResults.remove(sourceDbResults.size() - 1);

        Payment aa = Payment.builder().fileNo("aa").order(1).build();
        Payment bb = Payment.builder().fileNo("aa").order(1).build();
        sourceDbResults.add(aa);
        hiveResults.remove(0);
        hiveResults.remove(0);

        int sd = 0, ee = 100_000;

        List<List<Payment>> list = new ArrayList<>();

        for (; true; ) {
            list.add(sourceDbResults.subList(sd, ee));
            sd = ee;
            ee = ee + 100_000;
            if (sourceDbResults.size() < ee) {
                break;
            }
        }


        long start = System.currentTimeMillis();
        System.out.println("__" + start);

//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        for (int a = 0; a < 5000; a++) {
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("Çalışan Executer Service: " + Thread.currentThread().getName());
//                }
//            });
//        }


        ForkJoinPool customThreadPool = new ForkJoinPool(4);
        try {
            ForkJoinTask<String> submit = customThreadPool.submit(() -> {
                List<Payment> collect = sourceDbResults.parallelStream().filter(payment -> hiveResults.parallelStream().noneMatch(payment1 -> payment1.equals(payment))).collect(Collectors.toList());

                System.out.println(collect.size() + " agreement count");
                return "";
            });

            String s = submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        long end = (System.currentTimeMillis());
        System.out.println("paralelstream thread pool__" + (end - start));
        start = end;

        try {
            customThreadPool = new ForkJoinPool(4);
            ForkJoinTask<String> submit = customThreadPool.submit(() -> {
                List<Payment> collect = sourceDbResults.stream().filter(payment -> hiveResults.stream().noneMatch(payment1 -> payment1.equals(payment))).collect(Collectors.toList());

                System.out.println(collect.size() + " agreement count");
                return "";
            });

            String s = submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        end = (System.currentTimeMillis());
        System.out.println("stream trhread pool" + (end - start));
        start = end;

        customThreadPool = new ForkJoinPool(4);
        try {
            ForkJoinTask<String> submit = customThreadPool.submit(() -> {
                List<Payment> collect = sourceDbResults.parallelStream().filter(payment -> hiveResults.parallelStream().noneMatch(payment1 -> payment1.equals(payment))).collect(Collectors.toList());

                System.out.println(collect.size() + " agreement count");
                return "";
            });

            String s = submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        end = (System.currentTimeMillis());
        System.out.println("paralell stream thread pool" + (end - start));
        start = end;

        List<Payment> collect = sourceDbResults.parallelStream().filter(payment -> hiveResults.parallelStream().noneMatch(payment1 -> payment1.equals(payment))).collect(Collectors.toList());

        end = (System.currentTimeMillis());
        System.out.println("paralell stream" + (end - start));
        start = end;

        List<Payment> collect222 = sourceDbResults.stream().filter(payment -> hiveResults.stream().noneMatch(payment1 -> payment1.equals(payment))).collect(Collectors.toList());

        end = (System.currentTimeMillis());
        System.out.println("2__stream " + (end - start));
        start = end;

        List<Payment> collect1 = hiveResults.parallelStream().filter(payment -> {
            return sourceDbResults.parallelStream().noneMatch(payment1 -> payment1.equals(payment));
        }).collect(Collectors.toList());

        System.out.println("3__" + System.currentTimeMillis());

//
//        ForkJoinPool customThreadPool2= new ForkJoinPool(4);
//        try {
//            int sum = customThreadPool.submit(
//                    () ->  hiveResults.parallelStream().filter(payment ->
//                         sourceDbResults.stream().noneMatch(payment1 -> payment1.equals(payment))
//                    ).collect(Collectors.toList())
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }

//        compareSortedLists(
//                sourceDbResults, hiveResults,
//                inSourceDb_notInHive, inHive_notInSourceDb);

        assert inSourceDb_notInHive.equals(Arrays.asList(1, 4, 5, 8));
        assert inHive_notInSourceDb.equals(Arrays.asList(6, 7));
    }

    /**
     * Compares two sorted lists (or other iterable collections in ascending order).
     * Adds to onlyInList1 any and all elements in list1 that are not in list2; and
     * conversely to onlyInList2. The caller must ensure the two input lists are
     * already sorted and should initialize onlyInList1 and onlyInList2 to empty,
     * writable collections.
     */
    public static <T extends Comparable<? super T>> void compareSortedLists(Iterable<T> list1, Iterable<T> list2, Collection<T> onlyInList1, Collection<T> onlyInList2) {

        System.out.println(System.currentTimeMillis());

        Iterator<T> it1 = list1.iterator();
        Iterator<T> it2 = list2.iterator();
        T e1 = it1.hasNext() ? it1.next() : null;
        T e2 = it2.hasNext() ? it2.next() : null;
        while (e1 != null || e2 != null) {
            if (e2 == null) {  // No more elements in list2, some remaining in list1
                onlyInList1.add(e1);
                e1 = it1.hasNext() ? it1.next() : null;
            } else if (e1 == null) {  // No more elements in list1, some remaining in list2
                onlyInList2.add(e2);
                e2 = it2.hasNext() ? it2.next() : null;
            } else {
                int comp = e1.compareTo(e2);
                if (comp < 0) {
                    onlyInList1.add(e1);
                    e1 = it1.hasNext() ? it1.next() : null;
                } else if (comp > 0) {
                    onlyInList2.add(e2);
                    e2 = it2.hasNext() ? it2.next() : null;
                } else /* comp == 0 */ {
                    e1 = it1.hasNext() ? it1.next() : null;
                    e2 = it2.hasNext() ? it2.next() : null;
                }
            }
        }
        System.out.println(System.currentTimeMillis());
        System.out.println("end");

    }


}
