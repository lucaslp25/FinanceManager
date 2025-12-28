import { ChartConfiguration, ChartOptions } from "chart.js";

const BASE_OPTIONS: ChartOptions = {
    responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'bottom',
      labels: {
        usePointStyle: true,
        pointStyle: 'circle',
        padding: 20,
        font: {
          family: "'Helvetica Neue', 'Helvetica', 'Arial', sans-serif",
          size: 12
        }
      }
    },
    tooltip: {
      usePointStyle: true,
    }
  }
};

export function createChartOptions( titleText: string | null = null, customOverrides: ChartOptions = {}) : ChartConfiguration['options'] {

    const options: ChartOptions = JSON.parse(JSON.stringify(BASE_OPTIONS));

    if(titleText){
        if(!options.plugins) options.plugins = {};

        options.plugins.title = {
            display: true,
            text: titleText,
            font: {
                size: 16,
                weight: "bold"
            },
            padding: { bottom: 20 }
        };
    }

    return { ...options, ...customOverrides}
}