package dsw.gerumap.app.gui.swing.state;

import dsw.gerumap.app.gui.swing.state.states.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateManager {

    private AddLinijaState addLinijaState;
    private GlavniPojamState glavniPojamState;
    private AddPojamState addPojamState;
    private DeleteElementsState deleteElementsState;

    private SelectionElementsState selectionElementsState;
    private MoveState moveState;
    private State currentState;
    private ZoomInState zoomInState;
    private ZoomOutState zoomOutState;

    private DragPanelState dragPanelState;

    private RepositionState repositionState;

    public StateManager() {
        initialise();
    }

    private void initialise(){
        addLinijaState = new AddLinijaState();
        glavniPojamState = new GlavniPojamState();
        addPojamState = new AddPojamState();
        moveState = new MoveState();
        deleteElementsState = new DeleteElementsState();
        selectionElementsState = new SelectionElementsState();
        MoveState moveState = new MoveState();
        zoomInState = new ZoomInState();
        zoomOutState = new ZoomOutState();
        currentState = selectionElementsState;
        dragPanelState = new DragPanelState();
        repositionState = new RepositionState();
    }

    public void setAddLinijaState() {
        currentState = addLinijaState;
    }

    public void setGlavniPojamState(){
        currentState = glavniPojamState;
    }

    public void setAddPojamState() {
        currentState= addPojamState;
    }

    public void setDeleteElementsState() {
        currentState = deleteElementsState;
    }

    public void setSelectElementsState() {
        currentState = selectionElementsState;
    }

    public void setMoveState() {
        currentState = moveState ;
    }

    public void setZoomInState(){
        currentState = zoomInState;
    }
    public void setZoomOutState(){
        currentState = zoomOutState;
    }
    public void setDragPanelState(){
        currentState = dragPanelState;
    }
    public State getCurrentState() {
        return currentState;
    }

    public void setRepositionState(){
        currentState = repositionState;
    }


}
