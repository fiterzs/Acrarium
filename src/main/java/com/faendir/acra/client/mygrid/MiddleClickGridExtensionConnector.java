package com.faendir.acra.client.mygrid;

import com.faendir.acra.ui.view.base.MyGrid;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.connectors.grid.GridConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.data.DataCommunicatorConstants;
import com.vaadin.shared.ui.Connect;
import elemental.json.JsonObject;

/**
 * @author Lukas
 * @since 15.01.2018
 */
@Connect(MyGrid.MiddleClickExtension.class)
public class MiddleClickGridExtensionConnector extends AbstractExtensionConnector {
    @Override
    protected void extend(ServerConnector target) {
        Grid<JsonObject> grid = getParent().getWidget();
        grid.addDomHandler(event -> {
            if (event.getNativeButton() == NativeEvent.BUTTON_MIDDLE) {
                event.preventDefault();
                CellReference<JsonObject> cell = grid.getCellReference(event.getRelativeElement());
                getRpcProxy(Rpc.class).middleClick(cell.getRow().getString(DataCommunicatorConstants.KEY), getParent().getColumnId(cell.getColumn()),
                        MouseEventDetailsBuilder.buildMouseEventDetails(event.getNativeEvent(), event.getRelativeElement()));
            }
        }, MouseDownEvent.getType());
    }

    @Override
    public GridConnector getParent() {
        return (GridConnector) super.getParent();
    }

    public interface Rpc extends ServerRpc {
        void middleClick(String rowKey, String columnInternalId, MouseEventDetails details);
    }
}
