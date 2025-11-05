"use client";

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
import { BookOpen } from "lucide-react";
import { cn } from "@/lib/utils";

type TableProps = {
  columns: string[];
  rows: StudentMarks[] | StudentMarksPerSubject[];
  title: string;
};

const MarksTable = ({ columns, rows, title }: TableProps) => {
  return (
    <div>
      <Card className="bg-white/80 backdrop-blur-md shadow-lg border border-blue-100 rounded-2xl overflow-hidden">
        <CardHeader className="flex flex-row items-center justify-between border-b border-blue-100 bg-gradient-to-r from-blue-50 to-white px-6 py-4">
          <div className="flex items-center gap-2">
            <BookOpen className="text-blue-600" size={22} />
            <CardTitle className="text-lg font-semibold text-blue-700">
              {title}
            </CardTitle>
          </div>
        </CardHeader>

        <CardContent className="p-4">
          <div className="overflow-x-auto rounded-xl">
            <Table>
              <TableHeader>
                <TableRow className="bg-blue-50/50">
                  {columns.map((c) => (
                    <TableHead
                      key={c}
                      className="text-blue-900 font-medium text-sm uppercase tracking-wider"
                    >
                      {c}
                    </TableHead>
                  ))}
                </TableRow>
              </TableHeader>

              <TableBody>
                {rows.length === 0 ? (
                  <TableRow>
                    <TableCell
                      colSpan={columns.length}
                      className="text-center text-gray-500 italic py-6"
                    >
                      Aucune note disponible
                    </TableCell>
                  </TableRow>
                ) : (
                  rows.map((e, idx) => {
                    const id = "subjectId" in e ? e.subjectId : e.studentCin;
                    const name =
                      "subjectName" in e ? e.subjectName : e.studentName;

                    return (
                      <TableRow
                        key={id || idx}
                        className="border-b border-gray-100 hover:bg-blue-50/30 transition-colors"
                      >
                        <TableCell className="font-medium text-gray-800">
                          {id}
                        </TableCell>
                        <TableCell className="text-gray-700">{name}</TableCell>

                        {["ds", "oralOrTp", "exam"].map((key) => {
                          const value = e[key as keyof typeof e] as number;
                          const color = !value
                            ? "secondary"
                            : value >= 10
                            ? "success"
                            : "destructive";

                          return (
                            <TableCell key={key} className="text-left">
                              {value ? (
                                <Badge
                                  variant={
                                    color === "success"
                                      ? "default"
                                      : color === "destructive"
                                      ? "destructive"
                                      : "secondary"
                                  }
                                  className={cn(
                                    "px-3 py-1 text-sm font-semibold rounded-md ",
                                    color === "success" &&
                                      "bg-green-100 text-green-700",
                                    color === "destructive" &&
                                      "bg-red-100 text-red-700"
                                  )}
                                >
                                  {value.toFixed(1)}
                                </Badge>
                              ) : (
                                "-"
                              )}
                            </TableCell>
                          );
                        })}
                      </TableRow>
                    );
                  })
                )}
              </TableBody>
            </Table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default MarksTable;
