"use client";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
} from "@/components/ui/card";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import { Activity } from "lucide-react";

interface SubjectPerformanceChartProps {
  data: SubjectAverageStats[];
}

export default function SubjectPerformanceChart({
  data,
}: SubjectPerformanceChartProps) {
  if (data.length === 0) return null;

  return (
    <div className="w-full">
      <Card className="shadow-xl border border-indigo-100 hover:shadow-2xl transition-all duration-300">
        <CardHeader className="flex flex-row items-center justify-between">
          <div>
            <CardTitle className="text-lg font-semibold text-indigo-700">
              Taux de réussite par matière
            </CardTitle>
            <CardDescription className="text-gray-500 text-sm">
              Comparaison du nombre d’étudiants ayant validé ou non chaque
              matière
            </CardDescription>
          </div>
          <div className="flex items-center gap-2 text-indigo-600">
            <Activity size={22} />
            <span className="text-sm font-medium">Performance globale</span>
          </div>
        </CardHeader>

        <CardContent className="pt-4">
          <div className="h-[400px]">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart
                data={data}
                margin={{ top: 10, right: 30, left: 0, bottom: 20 }}
              >
                <CartesianGrid strokeDasharray="3 3" stroke="#E5E7EB" />
                <XAxis
                  dataKey="subjectName"
                  interval={0}
                  angle={-25}
                  textAnchor="end"
                  height={70}
                  tick={{ fill: "#4B5563", fontSize: 12 }}
                />
                <YAxis
                  tick={{ fill: "#4B5563", fontSize: 12 }}
                  domain={[0, "dataMax + 2"]}
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
                <Legend
                  wrapperStyle={{
                    paddingTop: "10px",
                    fontSize: "13px",
                  }}
                />
                <Bar
                  dataKey="passed"
                  name="Réussis"
                  fill="#4F46E5"
                  radius={[6, 6, 0, 0]}
                  animationDuration={900}
                />
                <Bar
                  dataKey="unpassed"
                  name="Non Réussis"
                  fill="#22C55E"
                  radius={[6, 6, 0, 0]}
                  animationDuration={900}
                />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
