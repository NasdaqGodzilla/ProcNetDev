package com.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class ProcNetDev extends HashMap<String, HashMap<String, String>> {
    protected static final String TAG = ProcNetDev.class.getSimpleName();

    public static final String PATH = "/proc/net/dev";

    private String[] fields;

    public ProcNetDev() {
        parseHeader(readHeader());
    }

    public void read(String... iface) {
        if (null == iface || 0 >= iface.length)
            return;

        final String[] rows = readContent(iface);

        if (null == rows || 0 >= rows.length)
            return;

        for (int i = 0; i < rows.length; ++i) {
            final String ifaceName = iface[i];
            String row = rows[i];

            if (null == row || row.isEmpty())
                continue;

            if (row.contains(":"))
                row = row.split(":")[1].trim();

            final String[] fields = row.split("\\s+");

            final HashMap<String, String> map = new HashMap<>();
            put(ifaceName, map);

            for (int j = 0; j < this.fields.length; ++j) {
                map.put(this.fields[j], fields[j]);
            }
        }
    }

    public String[] readContent(String... iface) {
        if (null == iface || 0 >= iface.length)
            return null;

        final String[] ret = new String[iface.length];

        try (BufferedReader br = new BufferedReader(new FileReader(PATH));) {
            String line = br.readLine();

            while (line != null && !line.isEmpty()) {
                line = line.trim();

                for (int i = 0; i < iface.length; ++i)
                    if (line.startsWith(iface[i]))
                        ret[i] =  line;

                line = br.readLine();
            }

        } catch (Exception e) { }

        return ret;
    }

    private String[] readHeader() {
        final String[] header = new String[2];

        try (BufferedReader br = new BufferedReader(new FileReader(PATH));) {
            for (int i = 0; i < header.length; ++i) {
                String line = br.readLine();

                if (null != line)
                    header[i] = line;
                else
                    header[i] = "";
            }
        } catch (Exception e) { }

        return header;
    }

    private void parseHeader(String[] header) {
        if (null == header || 2 > header.length)
            return;

        final String[] direction = header[0].replace('|', ' ').split("\\s+");

        if (null == direction || 3 > direction.length)
            return;

        final String[] face = header[1].split("\\|");

        if (null == face || 2 > face.length)
            return;

        final LinkedList<String> fieldsList = new LinkedList<>();

        for (int i = 1; i <= 2; ++i) {
            final String dir = direction[i];

            Arrays.stream(face[i].split("\\s+")).forEachOrdered(field -> {
                fieldsList.add(dir.concat("_").concat(field));
            });
        }

        fields = (String[]) fieldsList.toArray(new String[0]);
    }
}
