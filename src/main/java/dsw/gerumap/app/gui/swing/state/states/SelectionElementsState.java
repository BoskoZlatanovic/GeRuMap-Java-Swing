package dsw.gerumap.app.gui.swing.state.states;

import dsw.gerumap.app.core.Command;
import dsw.gerumap.app.mapRepository.commands.implementations.MoveElementsCommand;
import dsw.gerumap.app.gui.swing.tabbedPane.view.TabItemModel;
import dsw.gerumap.app.gui.swing.view.MainFrame;
import dsw.gerumap.app.gui.swing.view.painter.DevicePainter;
import dsw.gerumap.app.gui.swing.view.painter.PojamPainter;
import dsw.gerumap.app.gui.swing.view.painter.SelectioElements;
import dsw.gerumap.app.gui.swing.state.State;
import dsw.gerumap.app.mapRepository.implementation.Element;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SelectionElementsState extends State {

    Shape rectangle;

    int startX;
    int startY;

    int endX;

    int endY;

    boolean realesed = false;
    private DevicePainter currentylSelected;

    private Command moveElementsCommand;
    DevicePainter rectanglePainter = new SelectioElements(null);
    @Override
    public void execute(TabItemModel tb, Point point) {

        if(rectangle == null && tb.returnSelected(point) == null && currentylSelected == null){

            startX = point.x;
            startY = point.y;
            drawRectangle(point);
            tb.getPainters().add(rectanglePainter);
            tb.repaint();


        }
        else if(rectangle == null && tb.returnSelected(point) == null && currentylSelected != null){
            tb.getTabSelectionModel().getSelected().removeAll(tb.getTabSelectionModel().getSelected());
            currentylSelected = null;
            tb.repaint();
        }
        else if(tb.returnSelected(point) != null && tb.returnSelected(point) instanceof PojamPainter){

            tb.getTabSelectionModel().getSelected().removeAll(tb.getTabSelectionModel().getSelected());
            tb.getTabSelectionModel().getSelected().add(tb.returnSelected(point));
            currentylSelected = tb.returnSelected(point);
            realesed = false;
            if(rectangle != null){
                tb.getPainters().remove(rectanglePainter);
                rectangle = null;

            }
            tb.repaint();
        }

        else if(rectangle != null && !rectangle.contains(point)){
            tb.getTabSelectionModel().getSelected().removeAll(tb.getTabSelectionModel().getSelected());
            tb.getPainters().remove(rectanglePainter);
            rectangle = null;
            tb.repaint();
        }
        else if(rectangle != null && rectangle.contains(point)){

            List<Element> tmpElement = new ArrayList<>();

            for (DevicePainter dp: tb.getTabSelectionModel().getSelected()){
                tmpElement.add(dp.getElement());
            }

            moveElementsCommand = new MoveElementsCommand();
            ((MoveElementsCommand)moveElementsCommand).setElements(tmpElement);

            MainFrame.getIntance().getProjectView().switchToMoveState();
            MainFrame.getIntance().getProjectView().getStateManager().getCurrentState().execute(tb,point);
        }
        else if(rectangle == null && tb.returnSelected(point) != null ){
            tb.getTabSelectionModel().getSelected().add(tb.returnSelected(point));
            tb.repaint();
        }

    }

    @Override
    public void drag(TabItemModel tb, Point point) {

        if(rectangle == null && currentylSelected != null && !realesed) {

            moveElementsCommand = new MoveElementsCommand();
            List<Element> tmpElement = new ArrayList<>();
            tmpElement.add(tb.getTabSelectionModel().getSelected().get(0).getElement());
            ((MoveElementsCommand)moveElementsCommand).setElements(tmpElement);

            MainFrame.getIntance().getProjectView().switchToMoveState();
            MainFrame.getIntance().getProjectView().getStateManager().getCurrentState().execute(tb,point);

        }
        else if(rectangle != null){

            tb.getTabSelectionModel().getSelected().removeAll(tb.getTabSelectionModel().getSelected());
            drawRectangle(point);

            List<DevicePainter> newPainters = addSelected(tb);
            if(newPainters.size() != 0){
                tb.getTabSelectionModel().getSelected().addAll(newPainters);
            }
            tb.repaint();
        }


    }

    @Override
    public boolean isConnected(TabItemModel tb, Point point) {
        if(rectangle != null && tb.getTabSelectionModel().getSelected().size() == 0){
            tb.getPainters().remove(rectanglePainter);
            tb.repaint();
            rectangle = null;
        }
        realesed = true;
        return true;
    }

    private void drawRectangle(Point point){

        endX = point.x;
        endY = point.y;

        rectangle = new Rectangle2D.Float(startX,startY,Math.abs(endX-startX),Math.abs(endY-startY));

        rectanglePainter.setShape(rectangle);
    }
    private List<DevicePainter> addSelected(TabItemModel tb){

        List<DevicePainter> dv = new ArrayList<>();

        for (DevicePainter p: tb.getPainters()){
            if (rectangle.contains(p.getShape().getBounds2D()) && !(p instanceof SelectioElements)){
                dv.add(p);
            }
        }
        return dv;
    }
}
