package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

public final class Methods {
    public static File[] getFiles(String path) {
        return new File(path).listFiles();
    }
    public static String[] getFilePoints(String path) {
        int index = 1;
        int fileNameLengthMax = 0;
        final File[] fileCatalog = getFiles(path);
        String[] fileNameCatalog = new String[fileCatalog.length + 1];

        path = path.replace("%20"," ");

        for (File file : fileCatalog) {
            String fileName = file.getName();
            int fileNameLength = fileName.length();
            if (fileNameLengthMax < fileNameLength) {
                fileNameLengthMax = fileNameLength;
            }
            fileNameCatalog[index] = fileName;
            index++;
        }

        fileNameLengthMax++;

        StringBuilder spaceLine = new StringBuilder();
        for (int i = 0; i < 50; i++) { spaceLine.append(" "); }

        fileNameCatalog[0] = "Name:" + spaceLine;
        fileNameCatalog[0] += "Size:" + spaceLine;
        fileNameCatalog[0] += "Date:";

        for (int i = 1; i < fileNameCatalog.length; i++) {

            int fileNameLength = fileNameCatalog[i].length();
            double fileLength = (double) fileCatalog[i - 1].length()/(1024 * 1024);
            StringBuilder spaceFileLine = getSpaceLine(fileNameLength,50);

            fileNameCatalog[i] += spaceFileLine;
            if (fileLength > 0) {
                fileNameCatalog[i] += fileLength + "mb";
            }
            spaceFileLine = getSpaceLine(fileNameCatalog[i].length(),100);
            fileNameCatalog[i] += spaceFileLine;
            fileNameCatalog[i] += new Date(fileCatalog[i - 1].lastModified());
        }

        return fileNameCatalog;
    }
    public static String getHi() {
        return "Hi!";
    }
    public static String[] getFilePoints0(String path, HttpServletRequest request) {

        UserProfile us = AccountService.getUserBySessionId(request.getSession().getId());

        if (request.getParameter("path").contains("..")) {
            return new String[1];
        }

        if (!path.contains("D:\\Y_Projects\\Y_Java\\task_3\\filesUsers\\" + us.getLogin())) {
            return new String[1];
        }

        if (!path.contains("\\")) {
            path += "\\";
        }

        int index = 2;
        String test = "";
        final File[] fileCatalog = getFiles(path);
        StringBuilder strb = new StringBuilder();
        String[] pointCatalog = new String[fileCatalog.length + 2];

        pointCatalog[0] = strb.append(
                String.format(
                        "<p>Name:%sSize:%sDate:</p>",
                        " __________________________________________________ ",
                        " __________________________________________________ "
                )
        ).toString();

        strb = new StringBuilder();

        pointCatalog[1] = strb.append(
                String.format(
                        "<p><a href=\"%s\">UP</a></p>",
                        "filePointerJsp.jsp?path=" + path.substring(0,path.lastIndexOf('\\'))
                )
        ).toString();

        for (File file : fileCatalog) {

            strb = new StringBuilder();

            if (file.isFile()) {
                strb.append(
                    String.format(
                        "<p><a download href=\"http://localhost:8080/downloads?path=%s\">%s</a>%s" + file.length()/(1024 * 1024) + "mb%s%s" + " </p>",
                        file.getAbsoluteFile(),
                        file.getName(),
                        getSpaceLine(file.getName().length(),50),
                        getSpaceLine(file.getName().length(),100),
                        new Date(file.lastModified()).toString()
                    )
                );
            }
            else if (file.isDirectory()) {
                strb.append(
                    String.format(
                        "<p><a href=\"%s\">%s</a></p>",
                        "filePointerJsp.jsp?path=" + file.getAbsoluteFile(),
                        file.getName()
                    )
                );
            }

            pointCatalog[index] = strb.toString();
            index++;
        }

        return pointCatalog;
    }
    private static StringBuilder getSpaceLine(int lengthLine, int limit) {

        StringBuilder spaceLine = new StringBuilder();

        for (int i = lengthLine; i < limit; i++) { spaceLine.append("."); }

        return spaceLine;
    }
}
