package project;

import java.util.ArrayList;
import java.util.List;

public class TaskBreakDown {

    public String name, urgency, significance, deadline, target;
    public int currentIndex;

    public List<MainList> listElements = new ArrayList<>();
    public List<Sublist> sublistElements = new ArrayList<>();
    public List<Tasks> taskElements = new ArrayList<>();
    public List<Steps> stepsElements = new ArrayList<>();

    public MainList sublistParent;
    public Sublist tasksParent;
    public Tasks stepsParent;

    TaskBreakDown() {
    }

    TaskBreakDown(String name, String urgency, String significance, String deadline, String target) {
        this.name = name;
        this.urgency = urgency;
        this.significance = significance;
        this.deadline = deadline;
        this.target = target;
    }

    TaskBreakDown(Object parent, String name, String urgency, String significance, String deadline, String target) {
        this.name = name;
        this.urgency = urgency;
        this.significance = significance;
        this.deadline = deadline;
        this.target = target;
        if (parent instanceof MainList) {
            sublistParent = (MainList) parent;
        } else if (parent instanceof Sublist) {
            tasksParent = (Sublist) parent;
        } else if (parent instanceof Tasks) {
            stepsParent = (Tasks) parent;
        }
    }

    public String getTarget(Object o) {

        if (o instanceof MainList) {
            return ((MainList) o).target;
        } else if (o instanceof Sublist) {
            return ((Sublist) o).target;
        } else if (o instanceof Tasks) {
            return ((Tasks) o).target;
        } else if (o instanceof Steps) {
            return ((Steps) o).target;
        } else
            return "somethings fishy @ getTarget -> getDetails";

    }

    public void setTarget(Object o, String target) {

        if (o instanceof MainList) {
            ((MainList) o).target = target;
        } else if (o instanceof Sublist) {
            ((Sublist) o).target = target;
        } else if (o instanceof Tasks) {
            ((Tasks) o).target = target;
        } else if (o instanceof Steps) {
            ((Steps) o).target = target;
        }

    }

    public String getUrgency(Object o) {
        if (o instanceof MainList) {
            return ((MainList) o).urgency;
        } else if (o instanceof Sublist) {
            return ((Sublist) o).urgency;
        } else if (o instanceof Tasks) {
            return ((Tasks) o).urgency;
        } else if (o instanceof Steps) {
            return ((Steps) o).urgency;
        } else
            return "somethings fishy @  getUrgency -> getDetails";
    }

    public String getSignificance(Object o) {
        if (o instanceof MainList) {
            return ((MainList) o).significance;
        } else if (o instanceof Sublist) {
            return ((Sublist) o).significance;
        } else if (o instanceof Tasks) {
            return ((Tasks) o).significance;
        } else if (o instanceof Steps) {
            return ((Steps) o).significance;
        } else
            return "somethings fishy @ getSignificance -> getDetails";

    }

    public String getDetails(Object o) {
        if (o instanceof MainList) {
            return ((MainList) o).name;
        } else if (o instanceof Sublist) {
            return ((MainList) (((Sublist) o).sublistParent)).name + "`" + ((Sublist) o).name;
        } else if (o instanceof Tasks) {
            return ((MainList) (((Sublist) (((Tasks) o).tasksParent)).sublistParent)).name + "`"
                    + ((Sublist) (((Tasks) o).tasksParent)).name + "`" + ((Tasks) (o)).name;
        } else if (o instanceof Steps) {
            return ((MainList) (((Sublist) (((Tasks) (((Steps) (o)).stepsParent)).tasksParent)).sublistParent)).name
                    + "`" + ((Sublist) (((Tasks) (((Steps) (o)).stepsParent)).tasksParent)).name + "`"
                    + ((Tasks) (((Steps) (o)).stepsParent)).name + "`" + ((Steps) (o)).name;
        } else
            return "somethings fishy @ TaskBreakDown -> getDetails";

    }

    public void add(Object o) {
        if (o instanceof MainList) {
            listElements.add((MainList) o);
        } else if (o instanceof Sublist) {
            sublistElements.add((Sublist) o);
        } else if (o instanceof Tasks) {
            taskElements.add((Tasks) o);
        } else if (o instanceof Steps) {
            stepsElements.add((Steps) o);
        }
    }
    
    public boolean remove(int ind, Object o) {
        if (o instanceof MainList) {
            return listElements.remove(ind) != null;
        } else if (o instanceof Sublist) {
            return sublistElements.remove(ind) != null;
        } else if (o instanceof Tasks) {
            return taskElements.remove(ind) != null;
        } else if (o instanceof Steps) {
            return stepsElements.remove(ind) != null;
        } else {
            return false;
        }
    }

    public int getSize(Object o) {
        if (o instanceof MainList) {
            return listElements.size();
        } else if (o instanceof Sublist) {
            return sublistElements.size();
        } else if (o instanceof Tasks) {
            return taskElements.size();
        } else if (o instanceof Steps) {
            return stepsElements.size();
        } else {
            return -1;
        }
    }

    public int getIndexOf(Object o) {
        if (o instanceof MainList) {
            return currentIndex = listElements.indexOf((MainList) o);
        } else if (o instanceof Sublist) {
            return currentIndex = sublistElements.indexOf((Sublist) o);
        } else if (o instanceof Tasks) {
            return currentIndex = taskElements.indexOf((Tasks) o);
        } else if (o instanceof Steps) {
            return currentIndex = stepsElements.indexOf((Steps) o);
        } else {
            return -1;
        }
    }

}
