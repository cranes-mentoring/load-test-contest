import numpy as np
import matplotlib.pyplot as plt

def generate_custom_data():
    np.random.seed(42)
    requests = np.random.randint(1, 100, 100)
    response_times = np.random.uniform(0.1, 0.5, 50)  # 50% значений от 100 мс до 500 мс
    response_times = np.concatenate([response_times, np.random.uniform(0.8, 1.0, 50)])  # 50% значений выше
    return requests, response_times * 1000  # Умножаем на 1000 для представления в миллисекундах

def format_y_axis(value, _):
    if value >= 1000:
        return f'{value/1000:.1f} s'
    else:
        return f'{value:.0f} ms'

def plot_response_time_graph(requests, response_times):
    # Сортировка данных по числу запросов
    sorted_data = sorted(zip(requests, response_times), key=lambda x: x[0])
    requests, response_times = zip(*sorted_data)

    # Рассчет перцентилей
    percentiles_50 = np.percentile(response_times, 50, interpolation='linear')
    percentiles_90 = np.percentile(response_times, 90, interpolation='linear')
    percentiles_95 = np.percentile(response_times, 95, interpolation='linear')
    percentiles_99 = np.percentile(response_times, 99, interpolation='linear')

    # Рассчет среднего значения
    mean_response_time = np.mean(response_times)

    # Построение графика
    plt.plot(requests, response_times, label='Время отклика сервиса')
    plt.axhline(y=percentiles_50, color='r', linestyle='--', label='50-й перцентиль')
    plt.axhline(y=percentiles_90, color='g', linestyle='--', label='90-й перцентиль')
    plt.axhline(y=percentiles_95, color='m', linestyle='--', label='95-й перцентиль')
    plt.axhline(y=percentiles_99, color='y', linestyle='--', label='99-й перцентиль')
    plt.axhline(y=mean_response_time, color='b', linestyle='--', label='Среднее значение')
    plt.xlabel('Запросы')
    plt.ylabel('Время отклика')
    plt.gca().get_yaxis().set_major_formatter(plt.FuncFormatter(format_y_axis))
    plt.title('График времени отклика сервиса в зависимости от числа запросов')
    plt.legend()
    plt.show()

def main():
    requests, response_times = generate_custom_data()
    plot_response_time_graph(requests, response_times)

if __name__ == "__main__":
    main()
