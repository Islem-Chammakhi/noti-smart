"use client";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
} from "@/components/ui/card";
import {
  CartesianGrid,
  Line,
  LineChart,
  Tooltip,
  XAxis,
  YAxis,
  ResponsiveContainer,
} from "recharts";
import { TrendingUp } from "lucide-react";

interface AverageSubjectsPerFiliereProps {
  data: SubjectGeneralAverage[];
}

export default function AverageSubjectsPerFiliere({
  data,
}: AverageSubjectsPerFiliereProps) {
  if (data.length === 0) return null;

  return (
    <div className="w-full">
      <Card className="shadow-xl border border-indigo-100 hover:shadow-2xl transition-all duration-300">
        <CardHeader className="flex flex-row items-center justify-between">
          <div>
            <CardTitle className="text-lg font-semibold text-indigo-700">
              Moyennes Générales
            </CardTitle>
            <CardDescription className="text-gray-500 text-sm">
              Comparaison des moyennes par matière
            </CardDescription>
          </div>
          <div className="flex items-center gap-2 text-indigo-600">
            <TrendingUp size={22} />
            <span className="text-sm font-medium">Analyse dynamique</span>
          </div>
        </CardHeader>

        <CardContent className="pt-4">
          <div className="h-[360px]">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart
                data={data}
                margin={{ top: 10, right: 30, left: 10, bottom: 10 }}
              >
                <CartesianGrid strokeDasharray="3 3" stroke="#E5E7EB" />
                <XAxis
                  dataKey="subjectName"
                  interval={0}
                  angle={-20}
                  textAnchor="end"
                  height={70}
                  tick={{ fill: "#4B5563", fontSize: 12 }}
                />
                <YAxis
                  tick={{ fill: "#4B5563", fontSize: 12 }}
                  domain={[0, 20]}
                />
                <Tooltip
                  contentStyle={{
                    backgroundColor: "white",
                    borderRadius: "8px",
                    border: "1px solid #E5E7EB",
                    boxShadow: "0 4px 10px rgba(0,0,0,0.05)",
                  }}
                  labelStyle={{ color: "#4B5563" }}
                />
                <Line
                  type="monotone"
                  dataKey="average"
                  stroke="#4F46E5"
                  strokeWidth={3}
                  dot={{ r: 4, strokeWidth: 2, fill: "#6366F1" }}
                  activeDot={{ r: 6 }}
                />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
