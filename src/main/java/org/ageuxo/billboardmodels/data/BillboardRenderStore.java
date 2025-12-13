package org.ageuxo.billboardmodels.data;

import java.util.ArrayList;
import java.util.List;

public class BillboardRenderStore implements IBillboardRenderStore {

    private final List<BillboardPlacement> billboardPlacements = new ArrayList<>();

    public BillboardRenderStore() {
    }

    @Override
    public synchronized List<BillboardPlacement> getBillboardRenders() {
        return billboardPlacements;
    }

    @Override
    public synchronized boolean addBillboardRender(BillboardPlacement billboard) {
        return this.billboardPlacements.add(billboard);
    }

    @Override
    public synchronized void clearBillboardRenders() {
        this.billboardPlacements.clear();
    }

    @Override
    public synchronized void replaceBillboards(List<BillboardPlacement> replacementBillboards) {
        clearBillboardRenders();
        getBillboardRenders().addAll(replacementBillboards);
    }
}
