package org.jbei.ice.lib.utils;

import org.jbei.ice.lib.models.Storage.StorageType;

public class StorageUtils {

    public static String indexToWell(int index, StorageType type) {
        // wrap around is determined by storage type
        int factor;

        switch (type) {
        case PLATE96:
            factor = 12;
            break;

        default:
            throw new IllegalArgumentException("No handler for " + type);
        }

        String pos = "";
        int row = (index / factor);
        switch (row) {
        case 0:
            pos += "A";
            break;
        case 1:
            pos += "B";
            break;
        case 2:
            pos += "C";
            break;
        case 3:
            pos += "D";
            break;
        case 4:
            pos += "E";
            break;
        case 5:
            pos += "F";
            break;
        case 6:
            pos += "G";
            break;
        case 7:
            pos += "H";
            break;
        }

        int col = (index % factor) + 1;
        String colStr = col < 10 ? ("0" + String.valueOf(col)) : String.valueOf(col);
        return (pos += colStr);
    }

    public static int wellToIndex(String well) {
        return -1;
    }
}