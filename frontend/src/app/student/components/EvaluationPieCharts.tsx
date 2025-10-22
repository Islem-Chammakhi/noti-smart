"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  PieChart,
  Pie,
  Cell,
  Legend,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

export default function EvaluationPieCharts() {
  const fakeData = {
    DS: [
      { name: "≥ 10", value: 65 },
      { name: "< 10", value: 35 },
    ],
    TP: [
      { name: "≥ 10", value: 80 },
      { name: "< 10", value: 20 },
    ],
    EXAM: [
      { name: "≥ 10", value: 55 },
      { name: "< 10", value: 45 },
    ],
  };

  const COLORS = ["#8884d8", "#82ca9d"];

  const renderPie = (
    title: string,
    data: { name: string; value: number }[]
  ) => (
    <Card className="shadow-sm border border-indigo-100 flex flex-col items-center justify-center">
      <CardHeader>
        <CardTitle className="text-md text-center">{title}</CardTitle>
      </CardHeader>
      <CardContent className="w-full h-[250px]">
        <ResponsiveContainer width="100%" height="100%">
          <PieChart>
            <Pie
              data={data}
              cx="50%"
              cy="50%"
              labelLine={false}
              outerRadius={80}
              dataKey="value"
              label={({ name, value }) => `${name} (${value}%)`}
            >
              {data.map((entry, index) => (
                <Cell
                  key={`cell-${index}`}
                  fill={COLORS[index % COLORS.length]}
                />
              ))}
            </Pie>
            <Tooltip />
            <Legend />
          </PieChart>
        </ResponsiveContainer>
      </CardContent>
    </Card>
  );

  return (
    <div className="grid grid-cols-1 sm:grid-cols-3 gap-6">
      {renderPie("DS — Distribution des notes", fakeData.DS)}
      {renderPie("TP / Orale — Distribution des notes", fakeData.TP)}
      {renderPie("Examen — Distribution des notes", fakeData.EXAM)}
    </div>
  );
}
