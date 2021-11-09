package it.si2001.dto;

public class NgbDateRangeDTO {

    private NgbDateDTO start;
    private NgbDateDTO end;

    public NgbDateDTO getStart() {
        return start;
    }

    public void setStart(NgbDateDTO start) {
        this.start = start;
    }

    public NgbDateDTO getEnd() {
        return end;
    }

    public void setEnd(NgbDateDTO end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "NgbDateRangeDTO{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
