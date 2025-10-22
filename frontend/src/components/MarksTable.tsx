import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";

// type StudentGrades = {
//   id: number;
//   firstName: string;
//   lastName: string;
//   ds?: number;
//   tp?: number;
//   exam?: number;
// };

type TableProps = {
  columns: string[];
  rows: StudentMarks[] | StudentMarksPerSubject[];
  title: string;
};

const MarksTable = ({ columns, rows, title }: TableProps) => {
  return (
    <Card>
      <CardHeader>
        <CardTitle>{title}</CardTitle>
      </CardHeader>
      <CardContent>
        <Table>
          <TableHeader>
            <TableRow>
              {columns.map((c) => (
                <TableHead key={c}>{c}</TableHead>
              ))}
            </TableRow>
          </TableHeader>
          <TableBody>
            {rows.map((e, idx) => {
              if ("subjectId" in e) {
                return (
                  <TableRow key={e.subjectId}>
                    <TableCell>{e.subjectId}</TableCell>
                    <TableCell>{e.subjectName}</TableCell>
                    <TableCell>
                      {e.ds !== undefined && (
                        <Badge variant={e.ds >= 10 ? "default" : "destructive"}>
                          {e.ds.toFixed(1)}
                        </Badge>
                      )}
                    </TableCell>
                    <TableCell>
                      {e.oralOrTp !== undefined && (
                        <Badge
                          variant={e.oralOrTp >= 10 ? "default" : "destructive"}
                        >
                          {e.oralOrTp.toFixed(1)}
                        </Badge>
                      )}
                    </TableCell>
                    <TableCell>
                      {e.exam !== undefined && (
                        <Badge
                          variant={e.exam >= 10 ? "default" : "destructive"}
                        >
                          {e.exam.toFixed(1)}
                        </Badge>
                      )}
                    </TableCell>
                  </TableRow>
                );
              } else {
                return (
                  <TableRow key={e.studentCin || idx}>
                    <TableCell>{e.studentCin}</TableCell>
                    <TableCell>{e.studentName}</TableCell>
                    <TableCell>
                      {e.ds !== undefined && (
                        <Badge variant={e.ds >= 10 ? "default" : "destructive"}>
                          {e.ds.toFixed(1)}
                        </Badge>
                      )}
                    </TableCell>
                    <TableCell>
                      {e.oralOrTp !== undefined && (
                        <Badge
                          variant={e.oralOrTp >= 10 ? "default" : "destructive"}
                        >
                          {e.oralOrTp.toFixed(1)}
                        </Badge>
                      )}
                    </TableCell>
                    <TableCell>
                      {e.exam !== undefined && (
                        <Badge
                          variant={e.exam >= 10 ? "default" : "destructive"}
                        >
                          {e.exam.toFixed(1)}
                        </Badge>
                      )}
                    </TableCell>
                  </TableRow>
                );
              }
            })}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
};

export default MarksTable;
