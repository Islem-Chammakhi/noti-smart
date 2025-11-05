"use client";

import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
} from "@/components/ui/card";
import {
  Radar,
  RadarChart,
  PolarGrid,
  PolarAngleAxis,
  PolarRadiusAxis,
  ResponsiveContainer,
  Tooltip,
} from "recharts";
import { Target } from "lucide-react";

interface AverageSubjectsPerFiliereProps {
  data: SubjectGeneralAverage[];
}

const data = [
  { matiere: "Systèmes répartis", moyenne: 8 },
  { matiere: "Administration Systèmes & Réseaux", moyenne: 20 },
  { matiere: "Design Pattern & Conception", moyenne: 16 },
  { matiere: "Machine Learning", moyenne: 8 },
];

const SimpleRadarChart = ({ data }: AverageSubjectsPerFiliereProps) => {
  if (data.length === 0) return null;
  return (
    <div className="w-full">
      <Card className="shadow-xl border border-indigo-100 hover:shadow-2xl transition-all duration-300">
        <CardHeader className="flex flex-row items-center justify-between">
          <div>
            <CardTitle className="text-lg font-semibold text-indigo-700">
              Analyse des performances par matière
            </CardTitle>
            <CardDescription className="text-gray-500 text-sm">
              Visualisation radar des moyennes obtenues
            </CardDescription>
          </div>
          <div className="text-indigo-600">
            <Target size={22} />
          </div>
        </CardHeader>

        <CardContent className="flex items-center justify-center w-full h-[400px]">
          <ResponsiveContainer width="100%" height="100%">
            <RadarChart data={data} outerRadius="75%">
              <PolarGrid stroke="#E5E7EB" />
              <PolarAngleAxis
                dataKey="subjectName"
                tick={{ fill: "#4B5563", fontSize: 11 }}
              />
              <PolarRadiusAxis
                domain={[0, 20]}
                tick={{ fill: "#6B7280", fontSize: 10 }}
              />
              <Tooltip
                contentStyle={{
                  backgroundColor: "white",
                  borderRadius: "8px",
                  border: "1px solid #E5E7EB",
                  boxShadow: "0 4px 10px rgba(0,0,0,0.05)",
                }}
              />
              <Radar
                name="Moyenne"
                dataKey="average"
                stroke="#4F46E5"
                fill="#6366F1"
                fillOpacity={0.4}
                animationDuration={800}
              />
            </RadarChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>
    </div>
  );
};

export default SimpleRadarChart;
