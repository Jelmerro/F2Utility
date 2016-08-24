package f2utility.tools;

/**
 *
 * @author Jelmerro
 */
public interface Tool {

    public String activated = "-fx-border-color: #ADA;-fx-border-width: 2px;-fx-border-radius: 3px;";
    public String deactivated = "-fx-border-color: #bbb;-fx-border-width: 2px;-fx-border-radius: 3px;";

    public String processName(String name);

    public void checkActive();

    public void Activate();

    public void Deactivate();

    public void Reset();
}
