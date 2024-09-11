package edu.utdallas.cs4348;

public class PhamMainMemory extends MainMemory {
    public PhamMainMemory(int maxMemory, TLB tlb) {
        super(maxMemory / Util.SIZE_OF_FRAME, tlb);
        System.out.println("PhamMainMemory created with " + (maxMemory / Util.SIZE_OF_FRAME) + " frames.");
    }

    private int nextFrameToReplace = 0;

    @Override
    public void addPageToMemory(PageTableEntry pageTableEntry) {
        System.out.println("Adding page " + pageTableEntry.getPageNumber() + " to memory.");
        if (frames[nextFrameToReplace] != null && !frames[nextFrameToReplace].isEmpty()) {
            System.out.println("Frame " + nextFrameToReplace + " is not empty. Clearing it.");
            frames[nextFrameToReplace].clearFrame();
        }
        frames[nextFrameToReplace].assignFrame(pageTableEntry);
        System.out.println("Assigned page " + pageTableEntry.getPageNumber() + " to frame " + nextFrameToReplace + ".");
        nextFrameToReplace = (nextFrameToReplace + 1) % frames.length;
        if(tlb!=null){
            tlb.addEntry(pageTableEntry);
        }
    }

    @Override
    public void getPhysicalAddress(LookupInfo lookupInfo) {
        System.out.println("Getting physical address for logical address " + lookupInfo.getLogicalAddress() + ".");
        PageTableEntry pageTableEntry = lookupInfo.getProcess().getEntryAt(lookupInfo.getLogicalAddress() / Util.SIZE_OF_FRAME);
        if (pageTableEntry != null) {
            int frameNumber = pageTableEntry.getFrameNumber();
            if (tlb != null) {
                System.out.println("TLB is not null. Looking up frame number in TLB.");
                frameNumber = tlb.lookup(lookupInfo.getLogicalAddress() / Util.SIZE_OF_FRAME, lookupInfo.getProcess());
                if (frameNumber != -1) {
                    System.out.println("TLB hit. Frame number is " + frameNumber + ".");
                    lookupInfo.setTlbHit(true);
                } else {
                    System.out.println("TLB miss.");
                    tlb.addEntry(pageTableEntry);
                }
            }
            // If it's not a TLB hit, look up the page via the process's page table
            if (frameNumber == -1) {
                if (pageTableEntry != null) {
                    frameNumber = pageTableEntry.getFrameNumber();
                }
            }

            // Calculate the physical address
            if (frameNumber != -1) {
                int offset = lookupInfo.getLogicalAddress() & Util.LOCATION_WITHIN_PAGE_OR_FRAME_MASK;
                int physicalAddress = (frameNumber << Util.NUM_BITS_WITHIN_FRAME) | offset;
                lookupInfo.setPhysicalAddress(physicalAddress);
            } else {
                lookupInfo.setPhysicalAddress(-1); // Use -1 to indicate that page isn't in memory or invalid address
            }
        }
    }
}
