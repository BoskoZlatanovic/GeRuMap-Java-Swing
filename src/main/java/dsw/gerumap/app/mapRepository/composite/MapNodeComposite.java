package dsw.gerumap.app.mapRepository.composite;

import dsw.gerumap.app.serializable.Hide;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public abstract class MapNodeComposite extends MapNode {

   @Hide
    List<MapNode> children;

    public MapNodeComposite(MapNode parent, String name) {
        super(parent, name);
        this.children = new ArrayList<>();
    }

    public MapNodeComposite(MapNode parent, String name, List<MapNode> children) {
        super(parent, name);
        this.children = children;
    }

    public abstract void addChildren(MapNode child);

    public abstract void removeChildren(MapNode child);

    public boolean containsChild(String name){

        for (MapNode m: getChildren()){

            if (m.getName().equals(name)){
                    return true;
            }
        }
        return false;

    }

    public MapNode getChildByName(String name){
        for (MapNode child: children){
            if (name.equals(child.getName())){
                return child;
            }
        }
        return null;
    }


}
