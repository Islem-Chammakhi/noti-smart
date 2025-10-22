"use client";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  CartesianGrid,
  Line,
  LineChart,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

interface AverageSubjectsPerFiliereProps {
  data: SubjectGeneralAverage[];
}

export default function AverageSubjectsPerFiliere({
  data,
}: AverageSubjectsPerFiliereProps) {
  return (
    <>
      {data.length > 0 && (
        <div>
          <Card className="shadow-md border border-indigo-100">
            <CardHeader>
              <CardTitle className="text-lg">
                Moyennes générales — {"ING_2 INFO"}
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="h-[350px] min-w-max">
                <LineChart
                  style={{
                    width: "100%",
                    maxHeight: "40vh",
                    aspectRatio: 1.618,
                  }}
                  responsive
                  data={data}
                  margin={{
                    top: 10,
                    right: 30,
                    left: 0,
                    bottom: 0,
                  }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="subjectName"
                    interval={0}
                    angle={-15}
                    textAnchor="end"
                    height={80}
                  />
                  <YAxis />
                  <Tooltip />
                  <Line
                    type="monotone"
                    dataKey="average"
                    stroke="#8884d8"
                    fill="#8884d8"
                  />
                </LineChart>
              </div>
            </CardContent>
          </Card>
        </div>
      )}
    </>
  );
}
