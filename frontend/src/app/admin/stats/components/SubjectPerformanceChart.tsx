"use client";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  BarChart,
  Bar,
  Rectangle,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

interface SubjectPerformanceChartProps {
  data: SubjectAverageStats[];
}

export default function SubjectPerformanceChart({
  data,
}: SubjectPerformanceChartProps) {
  return (
    <div className="">
      {data.length > 0 && (
        <Card className="shadow-md border border-indigo-100 p-6">
          <CardHeader>
            <CardTitle className="text-lg">
              Taux de réussite par matière — {"ING_2 INFO"}
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[550px] min-w-max ">
              <BarChart
                style={{
                  width: "100%",
                  maxHeight: "70vh",
                  aspectRatio: 1.618,
                }}
                responsive
                data={data}
                margin={{
                  top: 5,
                  right: 0,
                  left: 0,
                  bottom: 5,
                }}
              >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis
                  dataKey="subjectName"
                  interval={0}
                  angle={-50}
                  textAnchor="end"
                  height={80}
                />
                <YAxis width="auto" />
                <Tooltip />
                <Legend />
                <Bar
                  dataKey="passed"
                  fill="#8884d8"
                  activeBar={<Rectangle fill="pink" stroke="blue" />}
                />
                <Bar
                  dataKey="unpassed"
                  fill="#82ca9d"
                  activeBar={<Rectangle fill="gold" stroke="purple" />}
                />
              </BarChart>
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  );
}
