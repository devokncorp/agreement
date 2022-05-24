//package com.example.demo;
//
//import com.example.demo.core.entity.Payment;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.util.*;
//import java.util.concurrent.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Slf4j
//public class DDD {
//    public static void main(String[] args) {
//        List<Payment> sourceDbResults = new ArrayList<>();//Arrays.asList(1, 2, 3, 4, 5, 8);
//        List<Payment> hiveResults = new ArrayList<>(); //Arrays.asList(2, 3, 6, 7);
//
//        List<Payment> inSourceDb_notInHive = new ArrayList<>();
//        List<Payment> inHive_notInSourceDb = new ArrayList<>();
//
//        int MAX = 3_000_000;
//        for (int i = 0; i < MAX; i++) {
//            sourceDbResults.add(Payment.builder().fileNo("lkkaaw" + String.valueOf(i)).order(i).build());
//            hiveResults.add(Payment.builder().fileNo("lkkaaw" + String.valueOf(i)).order(i).build());
//        }
//
//
//        sourceDbResults.remove(sourceDbResults.size() - 1);
//        Payment aa = Payment.builder().fileNo("aa").order(1).build();
//        sourceDbResults.add(aa);
//        hiveResults.remove(0);
//        hiveResults.remove(0);
//
//        int s = 0, e = 100;
//
//        List<List<Payment>> list = new ArrayList<>();
//
//        int limit = 10;
//
//        int startIndex = 0, endIndex = limit;
//
//        boolean flag = true;
//        for (; true; ) {
//            list.add(sourceDbResults.subList(startIndex, endIndex));
//            startIndex = endIndex;
//            endIndex += limit;
//
//            if (!flag) {
//                break;
//            }
//
//            if (endIndex > sourceDbResults.size()) {
//                endIndex = sourceDbResults.size();
//                flag = false;
//            }
//
//        }
//        while (flag) ;
//
//
//        long start = getCurrnet();
//        log.error("__" + start);
//
////        HashMap<String, Payment> reduce = sourceDbResults.stream().reduce(new HashMap<>(), (hashMap, e1) -> {
////            hashMap.merge(e1.getFileNo(), e1, (q1, q2) -> q1);
////            return hashMap;
////        }, (m, m2) -> {
////            m.putAll(m2);
////            return m;
////        });
//
//        long h23 = getCurrnet();
//        log.error("{}", getCurrnet());
//        ConcurrentHashMap<Integer, Payment> collectS = getIntegerPaymentConcurrentHashMap(sourceDbResults);
//        log.error("{}", (h23 - getCurrnet()));
//
//        long h2 = getCurrnet();
//        log.error("{}", getCurrnet());
//        ConcurrentHashMap<Integer, Payment> collect1 = (ConcurrentHashMap) hiveResults.parallelStream().collect(Collectors.toConcurrentMap(payment -> payment.hashCode(), Function.identity(), (u, v) -> {
//            log.error(u.getFileNo() + "-" + v.getFileNo() + " " + u.getOrder() + " " + v.getOrder());
//            return v;
//        }, () -> new ConcurrentHashMap(3000000)));
//
//
//        List<Payment> collect3 = collectS.keySet().parallelStream().filter(key -> collect1.get(key) == null).map(collectS::get).collect(Collectors.toList());
//
//
//        //Union of keys from both maps
////        HashSet<String> unionKeys = new HashSet<>(collectS.keySet());
////        unionKeys.addAll(collect1.keySet());
////        unionKeys.removeAll(collectS.keySet());
//
//
//        log.error("paralell stream thread pool start" + (getCurrnet()));
//        ForkJoinPool customThreadPool = new ForkJoinPool(4);
//
//        long h = getCurrnet();
//        log.error("{}", getCurrnet());
//        ConcurrentHashMap<String, Payment> collect12 = (ConcurrentHashMap) hiveResults.parallelStream().collect(Collectors.toConcurrentMap(payment -> payment.getFileNo(), Function.identity(), (p1, p2) -> {
//            log.error("{}", p1);
//            return p1;
//        }));
//        log.error("{}", (h - getCurrnet()));
//
//
//        log.error("paralell stream thread pool start" + (getCurrnet()));
//        ForkJoinTask<List<Payment>> submit = customThreadPool.submit(() -> {
//            List<Payment> collect2 = sourceDbResults.parallelStream().filter(payment ->
////                            hiveResults.parallelStream().noneMatch(payment1 -> payment1.equals(payment))
//            {
//                Payment paymentXXX = collect1.get(payment.getFileNo());
//                if (Objects.isNull(paymentXXX)) {
//                    return true;
//                }
//                return !paymentXXX.getOrder().equals(payment.getOrder());
//            }).collect(Collectors.toList());
//
//            if (collect2.size() > 0) {
//                log.error("collect2");
//            }
//            return collect2;
//        });
//
//        try {
//            List<Payment> payments = submit.get();
//            log.error("{}", payments.size());
//
//        } catch (InterruptedException ex) {
//            throw new RuntimeException(ex);
//        } catch (ExecutionException ex) {
//            throw new RuntimeException(ex);
//        }
//        log.error("paralell stream thread pool END" + (getCurrnet() - start));
//
//
//        log.error("paralell stream" + getCurrnet());
//        start = getCurrnet();
//
//
//        log.error("paralell stream " + (getCurrnet() - start));
//
//
//        ExecutorService executorService = Executors.newFixedThreadPool(8);
//
//
//        List<Payment> syncList = new ArrayList<>();
//        List<Payment> payments1 = Collections.synchronizedList(syncList);
//
//
//        List<Future> futureLit = new ArrayList<>();
//        for (List<Payment> payments : list) {
//            Future submit2 = executorService.submit(new Callable() {
//                @Override
//                public List<Payment> call() {
//                    log.error("START : " + payments.get(0).getFileNo() + " - " + Thread.currentThread().getName());
//                    long start2 = getCurrnet();
//
//                    List<Payment> collect = payments.stream().filter(payment -> hiveResults.stream().noneMatch(payment1 -> payment1.equals(payment))).collect(Collectors.toList());
//
////                    payments1.addAll(collect);
//
//                    log.error("END : " + Thread.currentThread().getName() + " size:" + (getCurrnet() - start2));
//                    return collect;
//                }
//            });
//            futureLit.add(submit);
//        }
//
//        log.error("done1");
//
//        long completedaskCount = 0;
////        try {
//        do {
//
//            if (completedaskCount < ((ThreadPoolExecutor) executorService).getCompletedTaskCount()) {
//                completedaskCount = ((ThreadPoolExecutor) executorService).getCompletedTaskCount();
//                log.error("getTaskCount:" + ((ThreadPoolExecutor) executorService).getTaskCount());
//                log.error("getCompletedTaskCount:" + ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
//                log.error("getActiveCount:" + ((ThreadPoolExecutor) executorService).getActiveCount());
//                log.error("querueRemainingCapacity:" + ((ThreadPoolExecutor) executorService).getQueue().remainingCapacity());
//
//            }
//        } while (((ThreadPoolExecutor) executorService).getActiveCount() > 0);
//
//        for (Future future : futureLit) {
//            try {
//                log.error(future.get().toString());
//            } catch (InterruptedException ex) {
//                throw new RuntimeException(ex);
//            } catch (ExecutionException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//
//        long end = getCurrnet();
//        log.error("paralelstream thread pool__" + (end - start));
//
//        log.error("done");
//        executorService.shutdown();
//
//        if (!executorService.isShutdown()) {
//            executorService.shutdownNow();
//        }
//        log.error("");
//
//
//        log.error("paralell stream" + getCurrnet());
//        start = getCurrnet();
//
//        List<Payment> collect2 = sourceDbResults.parallelStream().filter(payment -> hiveResults.parallelStream().noneMatch(payment1 -> payment1.equals(payment))).collect(Collectors.toList());
//
//        log.error("paralell stream " + (getCurrnet() - start));
//
//
//        File[] hiddenFiles = new File(".").listFiles(File::isHidden);
//    }
//
//    private static <T, U> ConcurrentHashMap<T, U> getIntegerPaymentConcurrentHashMap(List<U> entityList) {
//        return (ConcurrentHashMap) entityList
//                .parallelStream()
//                .collect(Collectors
//                        .toConcurrentMap(entity ->
//                                entity.hashCode(),
//                                Function.identity(),
//                                (u, v) -> u,
//                                () -> new ConcurrentHashMap(entityList.size())));
//    }
//
//    private static long getCurrnet() {
//        return System.currentTimeMillis();
//    }
//
//
//}
