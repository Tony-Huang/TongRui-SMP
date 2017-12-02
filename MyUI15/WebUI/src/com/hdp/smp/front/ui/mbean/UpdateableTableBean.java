package com.hdp.smp.front.ui.mbean;

import com.hdp.smp.front.ui.model.UpdateableCollectionModel;
import com.hdp.smp.front.ui.model.UpdateableModel;
import com.hdp.smp.model.SMPEntity;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;


public abstract class UpdateableTableBean {

    public UpdateableTableBean() {
    }


    public CollectionModel getModel() {
        if (_model == null) {
            _model = _createModel();
        }

        return _model;
    }

    //    public void insertStart(ActionEvent actionEvent) {
    //        CollectionModel model = getModel();
    //        Object oldKey = model.getRowKey();
    //        try {
    //            model.setRowIndex(0);
    //            insertAt(model.getRowKey());
    //        } finally {
    //            model.setRowKey(oldKey);
    //        }
    //    }

    //    public void insertBefore(ActionEvent actionEvent) {
    //        RowKeySet selection = getSelectedRowKeys();
    //        if (selection.getSize() > 0) {
    //            Object rowKey = selection.iterator().next();
    //            if (rowKey != null)
    //                insertAt(rowKey);
    //        }
    //    }

    //    public void insertAfter(ActionEvent actionEvent) {
    //        RowKeySet selection = getSelectedRowKeys();
    //        if (selection.getSize() > 0) {
    //            Object rowKey = selection.iterator().next();
    //            if (rowKey != null) {
    //                CollectionModel model = getModel();
    //                Object currKey = model.getRowKey();
    //                try {
    //                    model.setRowKey(rowKey);
    //                    if (model.isRowAvailable()) {
    //                        int nextIndex = model.getRowIndex() + 1;
    //                        if (model.isRowAvailable(nextIndex)) {
    //                            model.setRowIndex(nextIndex);
    //                            insertAt(model.getRowKey());
    //                        } else {
    //                            appendRow(actionEvent);
    //                        }
    //                    }
    //                } finally {
    //                    model.setRowKey(currKey);
    //                }
    //            }
    //        }
    //    }

    public void insertEnd(ActionEvent actionEvent) {
        CollectionModel model = getModel();
        Object oldKey = model.getRowKey();
        int lastRowIndex = model.getRowCount();
        try {
            model.setRowIndex(lastRowIndex);
            insertAt(model.getRowKey());
        } finally {
            model.setRowKey(oldKey);
        }
    }

    public void insertForEdit(ActionEvent actionEvent) {
        RowKeySet selection = getSelectedRowKeys();
        if (selection.getSize() > 0) {
            Object rowKey = selection.iterator().next();
            if (rowKey != null)
                insertAt(rowKey);
        }
    }

    public void deleteRow(ActionEvent actionEvent) {
        RowKeySet selection = getSelectedRowKeys();
        if (selection.getSize() > 0) {
            Object rowKey = selection.iterator().next();
            _deleteAt(rowKey);
        }
    }

    //    public void multipleRowDeleteMultipleEvents(ActionEvent actionEvent) {
    //        RowKeySet selection = getSelectedRowKeys();
    //        if (selection.getSize() > 0) {
    //            for (Object rowKey : selection) {
    //                _deleteAt(rowKey);
    //            }
    //        }
    //    }

    //    public void multipleRowDeleteOneEvent(ActionEvent actionEvent) {
    //        RowKeySet selection = getSelectedRowKeys();
    //        if (selection.getSize() > 0) {
    //            Object[] rows = new Object[selection.getSize()];
    //            int i = 0;
    //            for (Object rowKey : selection) {
    //                rows[i++] = rowKey;
    //            }
    //            CollectionModel model = getModel();
    //            ((UpdateableCollectionModel) model).deleteRows(rows);
    //        }
    //    }


    public void appendRow(ActionEvent actionEvent) {
        CollectionModel model = getModel();
        Object row = createRow();
        ((UpdateableModel) model).insertRow(null, row);
    }

    /**
     *update the current row
     * @param actionEvent
     */
    public void updateRow(ActionEvent actionEvent) {
        RowKeySet selection = getSelectedRowKeys();
        if (selection.getSize() > 0) {
            Object rowKey = selection.iterator().next();
            if (rowKey != null) {
                CollectionModel model = getModel();
                Object oldKey = model.getRowKey();

                try {
                    Object updatedRow = updateRow(rowKey);
                    ((UpdateableModel) model).updateRow(updatedRow);
                } finally {
                    model.setRowKey(oldKey);
                }
            }
        }
    }

  /**
     *update the specifed row (identified by rowkey)
     * @param rowKey
     * @return
     */
    protected abstract Object updateRow(Object rowKey) ;
//  {
//        CollectionModel model = getModel();
//        model.setRowKey(rowKey);
//        SMPEntity row = (SMPEntity) model.getRowData();
//        
//        return row;
//    }


    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public UIComponent getComponent() {
        return component;
    }

    protected RowKeySet getSelectedRowKeys() {
        return ((UIXTable) component).getSelectedRowKeys();
    }

    /**
     * need to be overwrite by subclass
     * @return
     */
    protected abstract Object createRow() ;
   // {
        //TableTestData.FileData row = new TableTestData.FileData(String.valueOf(_nextKey++), "", "", "", false);
  //      Object row = new Object();
 //       return row;
  //  }

    protected Object getRowKeyForRow(Object row) {
        Object rowKey = null;
        if (row instanceof SMPEntity)
            rowKey = ((SMPEntity) row).getEntityId();

        return rowKey;
    }

    protected void insertAt(Object rowKey) {
        CollectionModel model = getModel();
        Object row = createRow();
        Object oldKey = model.getRowKey();
        try {
            model.setRowKey(rowKey);
            ((UpdateableModel) model).insertRow(row);
        } finally {
            model.setRowKey(oldKey);
        }
    }

    private void _deleteAt(Object rowKey) {
        CollectionModel model = getModel();
        Object oldKey = model.getRowKey();
        try {
            model.setRowKey(rowKey);
            ((UpdateableModel) model).deleteRow();
        } finally {
            model.setRowKey(oldKey);
        }
    }

    protected  abstract UpdateableCollectionModel _createModel();
//    {
//        UpdateableCollectionModel model =
//            new UpdateableCollectionModel<ArrayList>(new TableTestData(15, false), _MODEL_KEY_FIELD);
//        _nextKey = model.getRowCount();
//        return model;
//    }


    protected UIComponent component;
    private UpdateableCollectionModel _model;
    private int _nextKey;
    private static final String _ACTIVE_ROW_KEY = "_afrActiveRowKey";
    private static final String _MODEL_KEY_FIELD = "no";

}
