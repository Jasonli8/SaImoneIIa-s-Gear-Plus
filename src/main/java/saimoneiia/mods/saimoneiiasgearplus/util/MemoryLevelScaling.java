package saimoneiia.mods.saimoneiiasgearplus.util;

public class MemoryLevelScaling {
    /*
        Each memory level will scale quadratically.
        At level n, the player will require (n+1)^2 more experience for the next level.
        Players start at level 0. Capped at level 100.

        Player will unlock mojority of recipes and entries by attaining levels via achievements.
     */
    public static int getMemLevel(int memProg) {
        int low = 0;
        if (getTotalMemForLevel(low) == memProg) return low;
        int high = 100;
        if (getTotalMemForLevel(high) == memProg) return high;
        while (true) {
            int mid = (int) Math.floor(((float) high + (float) low) / 2);
            int guessLowBound = getTotalMemForLevel(mid);
            int guessHighBound = getTotalMemForLevel(mid + 1);
            if (memProg >= guessLowBound && memProg < guessHighBound) {
                return mid;
            } else if (memProg < guessLowBound) {
                high = mid;
            } else {
                low = mid;
            }
        }
    }

    public static int getMemLevelProg(int memProg) {
        return memProg - getTotalMemForLevel(getMemLevel(memProg));
    }

    public static int getRequiredMemProg(int memProg) {
        if (isMaxLevel(memProg)) return -1;
        int nextLevel = getMemLevel(memProg) + 1;
        return nextLevel * nextLevel;
    }

    // level should be from [0,100]
    public static int getTotalMemForLevel(int level) {
        if (level <= 0) return 0;
        if (level > 100) level = 100;
        return (level*(level+1)*(2*level+1))/6;
    }

    public static boolean isMaxLevel(int memProg) {
        return memProg >= getTotalMemForLevel(100);
    }

    public static final int MAX_MEM = MemoryLevelScaling.getTotalMemForLevel(100);
    public static final int MIN_MEM = 0;
}
