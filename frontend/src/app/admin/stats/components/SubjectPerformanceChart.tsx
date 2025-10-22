"use client";
import { useState } from "react";
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

export default function SubjectPerformanceChart() {
  const [data, setData] = useState([]);
  const fakeStats = [
    { name: "Algorithmique", passed: 40, unpassed: 28 },
    { name: "Compilation", passed: 58, unpassed: 18 },
    { name: "Systèmes d’exploitation", passed: 11, unpassed: 57 },
    { name: "Base de données", passed: 58, unpassed: 10 },
    { name: "Programmation Web", passed: 16, unpassed: 52 },

    { name: "UML", passed: 13, unpassed: 55 },
    { name: "Génie logiciel", passed: 50, unpassed: 18 },
    { name: "Gestion de projet", passed: 60, unpassed: 8 },
    { name: "Test logiciel", passed: 63, unpassed: 5 },
    { name: "Développement mobile", passed: 65, unpassed: 3 },

    { name: "Réseaux 1", passed: 58, unpassed: 10 },
  ];

  //   useEffect(() => {
  //     if (!filiere) return;
  //     // Exemple de requête vers ton backend
  //     fetch(`/api/stats/moyennes?filiere=${filiere}`)
  //       .then(res => res.json())
  //       .then(setData)
  //       .catch(err => console.error(err));
  //   }, [filiere]);

  return (
    <div className="">
      {fakeStats && (
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
                data={fakeStats}
                margin={{
                  top: 5,
                  right: 0,
                  left: 0,
                  bottom: 5,
                }}
              >
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis
                  dataKey="name"
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
