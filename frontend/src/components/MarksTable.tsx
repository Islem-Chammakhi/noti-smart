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

type StudentGrades = {
  id: number;
  firstName: string;
  lastName: string;
  ds?: number;
  tp?: number;
  exam?: number;
};

type TableProps = {
  columns: string[];
  rows: StudentGrades[];
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
            {rows.map((e) => (
              <TableRow key={e.id}>
                <TableCell>{e.id}</TableCell>
                <TableCell>{e.firstName}</TableCell>
                <TableCell>{e.lastName}</TableCell>
                <TableCell>
                  {e.ds && (
                    <Badge variant={e.ds >= 10 ? "default" : "destructive"}>
                      {e.ds.toFixed(1)}
                    </Badge>
                  )}
                </TableCell>
                <TableCell>
                  {e.tp && (
                    <Badge variant={e.tp >= 10 ? "default" : "destructive"}>
                      {e.tp.toFixed(1)}
                    </Badge>
                  )}
                </TableCell>
                <TableCell>
                  {e.exam && (
                    <Badge variant={e.exam >= 10 ? "default" : "destructive"}>
                      {e.exam.toFixed(1)}
                    </Badge>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </CardContent>
    </Card>
  );
};

export default MarksTable;
