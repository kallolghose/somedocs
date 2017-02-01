Option Explicit

Dim excelObject
Set excelObject=CreateObject("Excel.Application")
Dim  strCurDir
strCurDir    = Wscript.Arguments(0)
excelObject.Workbooks.Open (strCurDir & "\vbemail.xlsm").Activate
excelObject.Application.Run "startmacro"
excelObject.Application.Quit
Set excelObject = Nothing