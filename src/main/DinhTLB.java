package edu.utdallas.cs4348;

public class DinhTLB extends TLB {
    @Override
    public void addEntry(PageTableEntry entry) {
        System.out.println("Attempting to add entry with page number " + entry.getPageNumber() + " to TLB.");
        int leastRecentlyUsedIndex = 0;
        long leastRecentTime = System.nanoTime();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                entries[i] = entry;
                entry.access();
                System.out.println("Added entry to empty slot at index " + i + ".");
                return;
            } else if (entries[i].getLastAccessed() < leastRecentTime) {
                leastRecentTime = entries[i].getLastAccessed();
                leastRecentlyUsedIndex = i;
                System.out.println("Found a less recently used entry at index " + i + " with last accessed time: " + leastRecentTime);
            }
        }
        System.out.println("No empty slot found. Replacing least recently used entry at index " + leastRecentlyUsedIndex + ".");
        entries[leastRecentlyUsedIndex] = entry;
        entry.access();
        System.out.println("Replaced entry at index " + leastRecentlyUsedIndex + " with new entry. New entry details: Page Number - " + entry.getPageNumber() + ", Process ID - " + entry.getProcessID() + ", Frame Number - " + entry.getFrameNumber());
    }


    @Override
    public int lookup(int pageInTable, Process process) {
        System.out.println("Looking up page number " + pageInTable + " for process ID " + process.getProcessID() + " in TLB.");
        if (entries.length == 0) {
            System.out.println("TLB is empty.");
        }
        for (int i = 0; i < entries.length; i++) {
            PageTableEntry entry = entries[i];
            if (entry.getProcessID() == process.getProcessID() && entry.getPageNumber() == pageInTable) {
                entry.access();
                System.out.println("TLB hit. Frame number is " + entry.getFrameNumber() + ".");
                return entry.getFrameNumber();
            }
        }
        System.out.println("TLB miss.");
        return -1;
    }

}
