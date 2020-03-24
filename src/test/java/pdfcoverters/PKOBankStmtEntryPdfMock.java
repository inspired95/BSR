package pdfcoverters;

import java.util.StringJoiner;

public class PKOBankStmtEntryPdfMock{
    private String firstLine;
    private String secondLine;
    private String thirdLine;

    public String getFirstLine() {
        return firstLine;
    }

    public String getSecondLine() {
        return secondLine;
    }

    public String getThirdLine() {
        return thirdLine;
    }

    private PKOBankStmtEntryPdfMock(String date, String ID, String typeDescription, String amount, String descLine1, String descLine2) {
        StringJoiner firstLine = new StringJoiner(" ");
        firstLine.add(date);
        firstLine.add(ID);
        firstLine.add( typeDescription );
        firstLine.add(amount);
        firstLine.add( "99999,99" );

        StringJoiner secondLine = new StringJoiner( " ");
        secondLine.add("31.12.2000");
        secondLine.add(descLine1);

        this.firstLine = firstLine.toString();
        this.secondLine = secondLine.toString();
        this.thirdLine = descLine2;
    }

    public static class Builder {
        private String date = "";
        private String ID = "";
        private String typeDescription = "";
        private String amount = "";
        private String descLine1 = "";
        private String descLine2 = "";

        public Builder setDate(String date ){
            this.date = date;
            return this;
        }

        public Builder setID(String ID )
        {
            this.ID = ID;
            return this;
        }

        public Builder setTypeDescription(String typeDescription){
            this.typeDescription = typeDescription;
            return this;
        }

        public Builder setAmount(String amount ){
            this.amount = amount;
            return this;
        }

        public Builder setDescLine1(String descLine1 ){
            this.descLine1 = descLine1;
            return this;
        }

        public Builder setDescLine2(String descLine2 ){
            this.descLine2 = descLine2;
            return this;
        }

        public PKOBankStmtEntryPdfMock build(){
            return new PKOBankStmtEntryPdfMock(date, ID, typeDescription, amount, descLine1, descLine2);
        }
    }
}
