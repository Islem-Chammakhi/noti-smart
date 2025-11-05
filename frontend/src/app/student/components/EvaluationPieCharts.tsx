"use client";

import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card";
import {
  PieChart,
  Pie,
  Cell,
  Legend,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { PieChart as PieIcon } from "lucide-react";

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

  const COLORS = ["#4F46E5", "#22C55E"];

  const renderPie = (
    title: string,
    description: string,
    data: { name: string; value: number }[]
  ) => (
    <div>
      <Card className="shadow-lg border border-indigo-100 hover:shadow-2xl transition-all duration-300">
        <CardHeader className="flex flex-row items-center justify-between">
          <div>
            <CardTitle className="text-md font-semibold text-indigo-700">
              {title}
            </CardTitle>
            <CardDescription className="text-gray-500 text-sm">
              {description}
            </CardDescription>
          </div>
          <div className="text-indigo-600">
            <PieIcon size={22} />
          </div>
        </CardHeader>

        <CardContent className="w-full h-[260px] flex items-center justify-center">
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
                isAnimationActive={true}
                animationDuration={800}
              >
                {data.map((entry, index) => (
                  <Cell
                    key={`cell-${index}`}
                    fill={COLORS[index % COLORS.length]}
                    stroke="#fff"
                    strokeWidth={2}
                  />
                ))}
              </Pie>
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
                  fontSize: "13px",
                  paddingTop: "10px",
                }}
              />
            </PieChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>
    </div>
  );

  return (
    <div className="grid grid-cols-1 sm:grid-cols-3 gap-6">
      {renderPie(
        "DS — Évaluations continues",
        "Distribution des notes DS",
        fakeData.DS
      )}
      {renderPie(
        "TP / Orale",
        "Performance des évaluations pratiques",
        fakeData.TP
      )}
      {renderPie(
        "Examen final",
        "Résultats globaux de l’examen",
        fakeData.EXAM
      )}
    </div>
  );
}
