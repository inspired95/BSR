# Bank statement reader

An application to analyse your bank account traffic
Features:
- ####Create a configuration which contains a map of categories where key is category's name and value is a list of phrases named "Keywords" in the app
  - Add new categories (Alt + C)
  - Remove categories 
  - Add keywords (Alt + K)
  - Remove keywords
  - Export configuration to a drive
  - Import configuration from a drive
  - Set current configuration as default configuration (Ctrl + S)
  - Load default configuration
- ####Create repository of bank operations
  - The list of bank operations is represented as a table with columns:
    - ID - filled in based on an ID field from a bank statement
    - Date - filled in based on a date field from a bank statement or date which was entered 
      manually 
    - Type - enum value selected based on an operation type from bank statement or NOT_APPLICABLE if bank operation added manually
    - Category - category selected based on created configuration. If a bank operation description 
      contains a phrase from a category then the category is assign to the bank operation.
    - Amount - a value of bank operation
    - Description - filled in based on a description of bank operation
    - Bank - bank name or NOT_APPLICABLE if bank operation added manually
    - File name - file name from which bank operation was loaded or NOT_APPLICABLE if bank operation added manually
   - Bank operations are groups in intervals based on year and month of the operation. 
   - An assigned group is represented by additional column - Interval
  - Export created repository to a drive
  - Load created repository from a drive
  - Read bank operations from bank statement files 
    - Supported files: pdf
    - Supported banks: PKO Bank Polski
  - Add bank operations manually to repository
  - Generate report based on repository


