package org.ageuxo.billboardmodels.data;

import java.util.List;

public interface IBillboardRenderStore {

    List<BillboardPlacement> getBillboardRenders();
    boolean addBillboardRender(BillboardPlacement billboard);
    void clearBillboardRenders();

    void replaceBillboards(List<BillboardPlacement> replacementBillboards);

    default void replaceFrom(IBillboardRenderStore other) {
        replaceBillboards(other.getBillboardRenders());
    }

}
