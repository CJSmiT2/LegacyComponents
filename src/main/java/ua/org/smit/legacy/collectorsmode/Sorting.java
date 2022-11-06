package ua.org.smit.legacy.collectorsmode;

import java.util.ArrayList;
import java.util.List;
import ua.org.smit.common.util.BubbleSort;

class Sorting {  // BAD ALL !!!

    List<Deal> sortDeals(List<Deal> items) {
        long[] times = new long[items.size()];
        for (int i = 0; i < times.length; i++) {
            times[i] = items.get(i).getCreatedTime().getTime();
        }

        new BubbleSort().sort(times);

        List<Deal> sorted = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            sorted.add(getByTime1(times[i], items));
        }
        return sorted;
    }

    private Deal getByTime1(long time, List<Deal> items) {
        for (Deal deal : items) {
            long itemTime = deal.getCreatedTime().getTime();
            if (itemTime == time) {
                return deal;
            }
        }
        throw new RuntimeException("Cant find Deal by time: " + time);
    }

    List<ImageCollect> sortImgCollect(List<ImageCollect> input) {
        long[] times = new long[input.size()];
        for (int i = 0; i < times.length; i++) {
            times[i] = input.get(i).getCreatedTime().getTime();
        }

        new BubbleSort().sort(times);

        List<ImageCollect> sorted = new ArrayList<>();
        for (int i = times.length - 1; i >= 0; i--) {
            sorted.add(getByTime2(times[i], input));
        }
        return sorted;
    }

    private ImageCollect getByTime2(long time, List<ImageCollect> items) {
        for (ImageCollect item : items) {
            long timeItem = item.getCreatedTime().getTime();
            if (timeItem == time) {
                return item;
            }
        }
        throw new RuntimeException("Cant find ImageCollect by time: " + time);
    }

}
