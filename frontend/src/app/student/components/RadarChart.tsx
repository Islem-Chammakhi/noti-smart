import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Radar,
  RadarChart,
  PolarGrid,
  PolarAngleAxis,
  PolarRadiusAxis,
} from "recharts";

const data = [
  {
    matiere: "Math",
    moyenne: 15,
  },
  {
    matiere: "Chinese",
    moyenne: 20,
  },
  {
    matiere: "English",
    moyenne: 16,
  },
  {
    matiere: "Geography",
    moyenne: 8,
  },
  {
    matiere: "Physics",
    moyenne: 11,
  },
  {
    matiere: "History",
    moyenne: 13,
  },
];

const SimpleRadarChart = () => {
  return (
    <Card className="shadow-sm border border-indigo-100 flex flex-col items-center justify-center">
      <CardHeader className="flex items-center justify-center">
        <CardTitle className="text-md text-center">Performance</CardTitle>
      </CardHeader>
      <CardContent className="flex items-center justify-center w-full h-full">
        <RadarChart
          style={{
            width: "100%",
            maxWidth: "500px",
            maxHeight: "80vh",
            aspectRatio: 1,
          }}
          responsive
          outerRadius="80%"
          data={data}
          margin={{
            top: 20,
            left: 20,
            right: 20,
            bottom: 20,
          }}
        >
          <PolarGrid />
          <PolarAngleAxis dataKey="matiere" />
          <PolarRadiusAxis />
          <Radar
            name="Mike"
            dataKey="moyenne"
            stroke="#8884d8"
            fill="#8884d8"
            fillOpacity={0.6}
          />
        </RadarChart>
      </CardContent>
    </Card>
  );
};

export default SimpleRadarChart;
