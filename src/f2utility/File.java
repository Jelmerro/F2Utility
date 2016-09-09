package f2utility;

import java.text.SimpleDateFormat;

/**
 * Extension on the File class
 *
 * @author Jelmerro
 */
public class File extends java.io.File {

    private String newName;

    /**
     * Constructor for a new file
     *
     * @param pathname String
     */
    public File(String pathname) {
        super(pathname);
        //The new name is initially equal to the current name
        //setNewName is used to change this after any configs
        newName = getName();

    }

    /**
     * Returns the current name, without the extension
     *
     * @return name String
     */
    @Override
    public String getName() {
        try {
            return super.getName().substring(0, super.getName().lastIndexOf("."));
        } catch (Exception ex) {
            return super.getName();
        }
    }

    /**
     * Returns the extension of the file (if any)
     *
     * @return ext String
     */
    public String getExt() {
        try {
            String ext = super.getName().substring(super.getName().lastIndexOf(".") + 1);
            if (ext.equals(super.getName())) {
                return "-";
            }
            return ext;
        } catch (Exception ex) {
            return "-";
        }
    }

    /**
     * Getter for newName
     *
     * @return newName String
     */
    public String getNewName() {
        return newName;
    }

    /**
     * Setter for newName, used when looping over the tools
     *
     * @param name String
     */
    public void setNewName(String name) {
        newName = name;
    }

    /**
     * Rename method calls the renameTo with the path and extension if needed
     *
     * @return success Boolean
     */
    public boolean rename() {
        String ext = getExt();
        if (ext.equals("-")) {
            return renameTo(new File(getParent() + separator + newName));
        } else {
            return renameTo(new File(getParent() + separator + newName + "." + ext));
        }
    }

    /**
     * Gets a formatted version of the last modified date
     *
     * @return lastModified String
     */
    public String getLastModified() {
        return "" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(lastModified());
    }

    /**
     * Gets a formatted version of the length of the file
     *
     * @return size String
     */
    public String getSize() {
        if (isDirectory()) {
            return "Folder";
        }
        long length = length();
        if (length < 1000) {
            return length + " B";
        }
        int exp = (int) (Math.log(length) / Math.log(1000));
        String pre = ("kMGTPE").charAt(exp - 1) + "B";
        return String.format("%.1f %s", length / Math.pow(1000, exp), pre);
    }
}
